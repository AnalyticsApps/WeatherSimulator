package com.weather.simulator.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.weather.simulator.connectors.ElevationAPIConnector;
import com.weather.simulator.connectors.IConnector;
import com.weather.simulator.connectors.WeatherAPIConnector;
import com.weather.simulator.dao.ElevationBean;
import com.weather.simulator.dao.LatLongBean;
import com.weather.simulator.dao.WeatherBean;
import com.weather.simulator.dao.WeatherForecastBean;
import com.weather.simulator.exception.WeatherSimulatorException;
import com.weather.simulator.helper.ConfigReader;
import com.weather.simulator.helper.WeatherPredictor;
import com.weather.simulator.utils.DateUtil;
import com.weather.simulator.utils.WeatherSimulatorConstants;

public class WeatherSimulatorService implements IWeatherSimulatorService {
	
	private static final Logger logger = Logger.getLogger(WeatherSimulatorService.class);

	/**
	 * Process the LatLongBean and return the weather forecast for that data point. 
	 * 
	 * @param latLongBean
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public WeatherForecastBean process(LatLongBean latLongBean) throws WeatherSimulatorException {

		// 1: Calculate the elevation based on Latitude/Longitude. Use elevation-api.io third party provider to get elevation.
		logger.info("Getting the Elevation based on lat/long data points.");
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("latitude", latLongBean.getLatitude());
		properties.put("longitude", latLongBean.getLongitude());
		ElevationAPIConnector elevationAPIConnector = new ElevationAPIConnector();
		ElevationBean elevationBean = (ElevationBean)elevationAPIConnector.convertToJSON(elevationAPIConnector.Connector(
				elevationAPIConnector.getURL(elevationAPIConnector.getURLTemplate(), properties)));
		elevationBean.setSuburb(latLongBean.getName());
		logger.info(String.format("ElevationBean: %s", elevationBean.toString()));
		
		
		// 2: Calculate the current weather based on Latitude/Longitude. Use api.darksky.net third party provider to get weather details.
		logger.info("Getting the current weather data.");
		IConnector weatherAPIConnector = new WeatherAPIConnector();
		properties.put("key", ConfigReader.getProperty(WeatherSimulatorConstants.WEATHER_CONNECTOR_KEY));
		WeatherBean currentWeatherBean = (WeatherBean)weatherAPIConnector.convertToJSON(weatherAPIConnector.Connector(
				weatherAPIConnector.getURL(weatherAPIConnector.getURLTemplate(), properties)));
		logger.info(String.format("CurrentWeatherBean: %s", currentWeatherBean.toString()));
	
		
		// 3: Get the weather data from yesterday to (LAST_N_DAYS_WEATHER) days. Use api.darksky.net third party provider to get historical weather details.
		logger.info("Getting the weather data for last N Days.");
		int lastNDays = Integer.parseInt(ConfigReader.getProperty(WeatherSimulatorConstants.LAST_N_DAYS_WEATHER));
		String[] lastNEpochDates = DateUtil.getLastNEpochDateFromNow( lastNDays, currentWeatherBean.getTimezone());
		WeatherBean[] lastNWeatherDataBean = new WeatherBean[lastNEpochDates.length + 1];
		int index = 0;
		for (String lastNEpochDate : lastNEpochDates) {
			properties.put("date", lastNEpochDate);
			lastNWeatherDataBean[index] = (WeatherBean)weatherAPIConnector.convertToJSON(weatherAPIConnector.Connector(
					weatherAPIConnector.getURL(weatherAPIConnector.getURLTemplate(), properties)));
			index = index + 1;
		}
		// Add current Weather to lastNWeatherDataBean
		lastNWeatherDataBean[lastNEpochDates.length] = currentWeatherBean;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			logger.info(String.format("lastNWeatherDataBean: %s", weatherBean.toString()));
		}
		
		// 4: Populate the weather data for last year - current date, previous & next N days
		logger.info("Getting weather data for last year - current date, previous & next N days");
		String[] lastYearPreviousNEpochDate = DateUtil.getNEpochDatesOnLastYear((lastNDays * -1 ), currentWeatherBean.getTimezone());
		String[] lastYearEpochDate = DateUtil.getNEpochDatesOnLastYear(0, currentWeatherBean.getTimezone());		
		String[] lastYearNextNEpochDate = DateUtil.getNEpochDatesOnLastYear((lastNDays - 1), currentWeatherBean.getTimezone());
		String[] epochlastYearDates = ArrayUtils.addAll(ArrayUtils.addAll(lastYearPreviousNEpochDate, lastYearEpochDate), lastYearNextNEpochDate);		
		WeatherBean[] lastYearWeatherDataBean = new WeatherBean[epochlastYearDates.length];
		index = 0;
		for (String date : epochlastYearDates) {
			properties.put("date", date);
			lastYearWeatherDataBean[index] = (WeatherBean)weatherAPIConnector.convertToJSON(weatherAPIConnector.Connector(
					weatherAPIConnector.getURL(weatherAPIConnector.getURLTemplate(), properties)));
			index++;
		}

		
		// 5: Calculate the new weather data.
		logger.info("Calculate the new weather data.");
		WeatherPredictor predictor = new WeatherPredictor();
		WeatherForecastBean forcastBean = predictor.forecast(elevationBean, lastNWeatherDataBean, lastYearWeatherDataBean);
		
		
		return forcastBean;
	}

}
