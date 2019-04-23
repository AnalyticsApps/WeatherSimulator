package com.weather.simulator.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.weather.simulator.dao.ElevationBean;
import com.weather.simulator.dao.WeatherBean;
import com.weather.simulator.dao.WeatherForecastBean;


/**
 * Test Cases for predicting the weather attributes.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherPredictorTest {

	@Test
	public void forecastTest() throws Exception {
		/**
		 * Test Case - Load the ElevationBean & array of WeatherBean and predict the attributes.
		 */		
		
		ElevationBean elevationBean = new ElevationBean();
		elevationBean.setLatitude("-33.803829");
		elevationBean.setLongitude("150.987686");
		elevationBean.setSuburb("Westmead");
		elevationBean.setElevation("34");
		
		WeatherBean[] recentWeatherDataBean = new WeatherBean[3];
		recentWeatherDataBean[0] = new WeatherBean();
		recentWeatherDataBean[0].setLatitude("-33.803829");
		recentWeatherDataBean[0].setLongitude("150.987686");
		recentWeatherDataBean[0].setTimezone("Australia/Sydney");
		recentWeatherDataBean[0].setTime("1555918804");
		recentWeatherDataBean[0].setSummary("Clear");
		recentWeatherDataBean[0].setTemperature("76.02");
		recentWeatherDataBean[0].setCloudCover(" 0.1");
		recentWeatherDataBean[0].setDewPoint("61.62");
		recentWeatherDataBean[0].setHumidity("0.61");
		recentWeatherDataBean[0].setPressure("1022.84");
		recentWeatherDataBean[0].setWindSpeed("10.57");

		recentWeatherDataBean[1] = new WeatherBean();
		recentWeatherDataBean[1].setLatitude("-33.803829");
		recentWeatherDataBean[1].setLongitude("150.987686");
		recentWeatherDataBean[1].setTimezone("Australia/Sydney");
		recentWeatherDataBean[1].setTime("1555768800");
		recentWeatherDataBean[1].setSummary("Partly Cloudy");
		recentWeatherDataBean[1].setTemperature("67.42");
		recentWeatherDataBean[1].setCloudCover("0.42");
		recentWeatherDataBean[1].setDewPoint("65.05");
		recentWeatherDataBean[1].setHumidity("0.92");
		recentWeatherDataBean[1].setPressure("1023.96");
		recentWeatherDataBean[1].setWindSpeed("3.52");

		recentWeatherDataBean[2] = new WeatherBean();
		recentWeatherDataBean[2].setLatitude("-33.803829");
		recentWeatherDataBean[2].setLongitude("150.987686");
		recentWeatherDataBean[2].setTimezone("Australia/Sydney");
		recentWeatherDataBean[2].setTime("1555682400");
		recentWeatherDataBean[2].setSummary("Clear");
		recentWeatherDataBean[2].setTemperature("63.01");
		recentWeatherDataBean[2].setCloudCover("0");
		recentWeatherDataBean[2].setDewPoint("60.68");
		recentWeatherDataBean[2].setHumidity("0.92");
		recentWeatherDataBean[2].setPressure("1026.34");
		recentWeatherDataBean[2].setWindSpeed("2.93");
		
		WeatherBean[] lastYearWeatherDataBean = new WeatherBean[3];
		lastYearWeatherDataBean[0] = new WeatherBean();
		lastYearWeatherDataBean[0].setLatitude("-33.803829");
		lastYearWeatherDataBean[0].setLongitude("150.987686");
		lastYearWeatherDataBean[0].setTimezone("Australia/Sydney");
		lastYearWeatherDataBean[0].setTime("1555918804");
		lastYearWeatherDataBean[0].setSummary("Clear");
		lastYearWeatherDataBean[0].setTemperature("79.02");
		lastYearWeatherDataBean[0].setCloudCover(" 0.1");
		lastYearWeatherDataBean[0].setDewPoint("62.62");
		lastYearWeatherDataBean[0].setHumidity("0.51");
		lastYearWeatherDataBean[0].setPressure("1302.84");
		lastYearWeatherDataBean[0].setWindSpeed("15.57");

		lastYearWeatherDataBean[1] = new WeatherBean();
		lastYearWeatherDataBean[1].setLatitude("-33.803829");
		lastYearWeatherDataBean[1].setLongitude("150.987686");
		lastYearWeatherDataBean[1].setTimezone("Australia/Sydney");
		lastYearWeatherDataBean[1].setTime("1555768800");
		lastYearWeatherDataBean[1].setSummary("Partly Cloudy");
		lastYearWeatherDataBean[1].setTemperature("65.42");
		lastYearWeatherDataBean[1].setCloudCover("0.49");
		lastYearWeatherDataBean[1].setDewPoint("69.05");
		lastYearWeatherDataBean[1].setHumidity("0.92");
		lastYearWeatherDataBean[1].setPressure("1043.96");
		lastYearWeatherDataBean[1].setWindSpeed("5.52");

		lastYearWeatherDataBean[2] = new WeatherBean();
		lastYearWeatherDataBean[2].setLatitude("-33.803829");
		lastYearWeatherDataBean[2].setLongitude("150.987686");
		lastYearWeatherDataBean[2].setTimezone("Australia/Sydney");
		lastYearWeatherDataBean[2].setTime("1555682400");
		lastYearWeatherDataBean[2].setSummary("Clear");
		lastYearWeatherDataBean[2].setTemperature("64.01");
		lastYearWeatherDataBean[2].setCloudCover("0");
		lastYearWeatherDataBean[2].setDewPoint("62.68");
		lastYearWeatherDataBean[2].setHumidity("0.82");
		lastYearWeatherDataBean[2].setPressure("1036.34");
		lastYearWeatherDataBean[2].setWindSpeed("4.93");
		
		WeatherPredictor predict = new WeatherPredictor();
		WeatherForecastBean forecastBean = predict.forecast(elevationBean, recentWeatherDataBean, lastYearWeatherDataBean);
		
		assertEquals("Westmead", forecastBean.getLocation());
		assertEquals("-33.803829", forecastBean.getLatitude());
		assertEquals("150.987686", forecastBean.getLongitude());
		assertEquals("34", forecastBean.getElevation());
		  
	}
	
}
