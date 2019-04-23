package com.weather.simulator.helper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;

import org.apache.log4j.Logger;

import com.weather.simulator.dao.ElevationBean;
import com.weather.simulator.dao.WeatherBean;
import com.weather.simulator.dao.WeatherForecastBean;

/**
 * Predict the weather based on historical & recent weather data.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherPredictor {

	private static final Logger logger = Logger.getLogger(WeatherPredictor.class);
	
	/**
	 * Forecast the weather based on recent weather data & historical data.
	 * 
	 * @param elevationBean
	 * @param lastNWeatherDataBean
	 * @param lastYearWeatherDataBean
	 * @return
	 */
	public WeatherForecastBean forecast(ElevationBean elevationBean, WeatherBean[] lastNWeatherDataBean, WeatherBean[] lastYearWeatherDataBean) {

		WeatherForecastBean forecastBean = new WeatherForecastBean();
		forecastBean.setLocation(elevationBean.getSuburb());
		forecastBean.setLatitude(elevationBean.getLatitude());
		forecastBean.setLongitude(elevationBean.getLongitude());
		forecastBean.setElevation(elevationBean.getElevation());

		ZoneId id = ZoneId.of(lastNWeatherDataBean[0].getTimezone());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		LocalDateTime currentDate = LocalDateTime.now().plusDays(1);
		ZonedDateTime zonedDateTime = ZonedDateTime.of(currentDate, id);
		// Set the Date based on timezone
		forecastBean.setDate(zonedDateTime.format(dateFormat));

		WeatherBean recentWeather = lastNWeatherDataBean[lastNWeatherDataBean.length - 1];
		
		logger.info(String.format("Recent WeatherBean: %s", recentWeather.toString()));
		
		// Predicting the features.
		double predictedTemperature = predictTemperature(lastNWeatherDataBean);
		double predictedCloudCover = predictCloudCover(lastNWeatherDataBean);
		double predictedDewPoint = predictDewPoint(lastNWeatherDataBean);
		double predictedHumidity = predictHumidity(lastNWeatherDataBean);
		double predictedPressure = predictPressure(lastNWeatherDataBean);
		double predictedWindSpeed = predictWindSpeed(lastNWeatherDataBean);


		forecastBean.setTemperature(String.format("%.2f", predictedTemperature));
		forecastBean.setCloudCover(String.format("%.2f", predictedCloudCover));
		forecastBean.setDewPoint(String.format("%.2f", predictedDewPoint));
		forecastBean.setHumidity(String.format("%.2f", predictedHumidity));
		forecastBean.setPressure(String.format("%.2f", predictedPressure));
		forecastBean.setWindSpeed(String.format("%.2f", predictedWindSpeed));
		
		// Predict the Summary based on last year weather data.
		String predictedSummary = predictSummary(lastYearWeatherDataBean, forecastBean);
		forecastBean.setSummary(predictedSummary);
		
		logger.info(String.format("WeatherForecastBean: %s", forecastBean.toString()));

		return forecastBean;

	}

	/**
	 * Predict the Wind Speed  feature. 
	 * Calculate the std deviation and calculate the random double with the std limit add it with mean.
	 * 
	 * @param lastNWeatherDataBean
	 * @return
	 */
	private double predictWindSpeed(WeatherBean[] lastNWeatherDataBean) {
		double windSpeedArray[] = new double[lastNWeatherDataBean.length];
		int index = 0;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			windSpeedArray[index] = Double.parseDouble(weatherBean.getWindSpeed());
			index++;
		}

		double variance = calculateVariance(windSpeedArray);
		double std = Math.sqrt(variance);
		
		double factor = calculateRandomDouble(0, std);
		
		double mean = Arrays.stream(windSpeedArray).average().getAsDouble();
		
		return (mean + factor);
	}

	/**
	 * Predict the Pressure  feature.
	 * Calculate the std deviation and calculate the random double with the std limit add it with mean.
	 * 
	 * @param lastNWeatherDataBean
	 * @return
	 */
	private double predictPressure(WeatherBean[] lastNWeatherDataBean) {
		double pressureArray[] = new double[lastNWeatherDataBean.length];
		int index = 0;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			pressureArray[index] = Double.parseDouble(weatherBean.getPressure());
			index++;
		}

		double variance = calculateVariance(pressureArray);
		double std = Math.sqrt(variance);
		
		double factor = calculateRandomDouble(0, std);
		
		double mean = Arrays.stream(pressureArray).average().getAsDouble();
		
		return (mean + factor);
	}

	/**
	 * Predict the Humidity  feature.
	 * Calculate the std deviation and calculate the random double with the std limit add it with mean.
	 * 
	 * @param lastNWeatherDataBean
	 * @return
	 */
	private double predictHumidity(WeatherBean[] lastNWeatherDataBean) {
		double humidityArray[] = new double[lastNWeatherDataBean.length];
		int index = 0;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			humidityArray[index] = Double.parseDouble(weatherBean.getHumidity());
			index++;
		}

		double variance = calculateVariance(humidityArray);
		double std = Math.sqrt(variance);
		
		double factor = calculateRandomDouble(0, std);
		
		double mean = Arrays.stream(humidityArray).average().getAsDouble();
		
		return (mean + factor);
	}

	/**
	 * Predict the Dew Point  feature.
	 * Calculate the std deviation and calculate the random double with the std limit add it with mean.
	 * 
	 * @param lastNWeatherDataBean
	 * @return
	 */
	private double predictDewPoint(WeatherBean[] lastNWeatherDataBean) {
		double dewPointArray[] = new double[lastNWeatherDataBean.length];
		int index = 0;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			dewPointArray[index] = Double.parseDouble(weatherBean.getDewPoint());
			index++;
		}

		double variance = calculateVariance(dewPointArray);
		double std = Math.sqrt(variance);
		
		double factor = calculateRandomDouble(0, std);
		
		double mean = Arrays.stream(dewPointArray).average().getAsDouble();
		
		return (mean + factor);
	}

	/**
	 * Predict the Cloud Cover  feature.
	 * Calculate the std deviation and calculate the random double with the std limit add it with mean.
	 * 
	 * @param lastNWeatherDataBean
	 * @return
	 */
	private double predictCloudCover(WeatherBean[] lastNWeatherDataBean) {
		double cloudCoverArray[] = new double[lastNWeatherDataBean.length];
		int index = 0;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			cloudCoverArray[index] = Double.parseDouble(weatherBean.getCloudCover());
			index++;
		}

		double variance = calculateVariance(cloudCoverArray);
		double std = Math.sqrt(variance);
		
		double factor = calculateRandomDouble(0, std);
		
		double mean = Arrays.stream(cloudCoverArray).average().getAsDouble();
		
		return (mean + factor);
	}

	/**
	 * Predict the Temperature feature.
	 * Calculate the std deviation and calculate the random double with the std limit add it with mean.
	 * 
	 * @param lastNWeatherDataBean
	 * @return
	 */
	private double predictTemperature(WeatherBean[] lastNWeatherDataBean) {
		double temperatureArray[] = new double[lastNWeatherDataBean.length];
		int index = 0;
		for (WeatherBean weatherBean : lastNWeatherDataBean) {
			temperatureArray[index] = Double.parseDouble(weatherBean.getTemperature());
			index++;
		}

		double variance = calculateVariance(temperatureArray);
		double std = Math.sqrt(variance);
		
		double factor = calculateRandomDouble(0, std);
	
		double mean = Arrays.stream(temperatureArray).average().getAsDouble();
		
		return (mean + factor);
	}

	/**
	 * Calculate the Variance of an feature.
	 * 
	 * @param itemsArray
	 * @return
	 */
	private double calculateVariance(double[] itemsArray) {
		double average = Arrays.stream(itemsArray).average().getAsDouble();

		double totalChange = 0;
		for (double item : itemsArray) {
			totalChange += Math.pow((item - average), 2);
		}

		double variance = totalChange / itemsArray.length;
		return variance;
	}

	/**
	 * Calculate a random value within std deviation limit
	 * 
	 * @param rangeMin
	 * @param rangeMax
	 * @return
	 */
	private double calculateRandomDouble(double rangeMin, double rangeMax) {
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;

	}
	
	/**
	 * Calculate the Euclidean distance between forecasted Weather and last year weather data and get the summary 
	 * that have minimum distance with forecastBean. 
	 * 
	 * @param lastYearWeatherDataBean
	 * @param forecastBean
	 * @return
	 */
	private String predictSummary(WeatherBean[] lastYearWeatherDataBean, WeatherForecastBean forecastBean) {
		double[] distance = new double[lastYearWeatherDataBean.length]; 
		int index = 0;
		// Calculate the Euclidean distance
		for (WeatherBean weatherBean : lastYearWeatherDataBean) {
			distance[index] = Math.pow(Double.parseDouble(weatherBean.getTemperature()) - Double.parseDouble(forecastBean.getTemperature()), 2.0);
			distance[index] += Math.pow(Double.parseDouble(weatherBean.getCloudCover()) - Double.parseDouble(forecastBean.getCloudCover()), 2.0);
			distance[index] += Math.pow(Double.parseDouble(weatherBean.getDewPoint()) - Double.parseDouble(forecastBean.getDewPoint()), 2.0);			
			distance[index] += Math.pow(Double.parseDouble(weatherBean.getHumidity()) - Double.parseDouble(forecastBean.getHumidity()), 2.0);
			distance[index] += Math.pow(Double.parseDouble(weatherBean.getPressure()) - Double.parseDouble(forecastBean.getPressure()), 2.0);
			distance[index] += Math.pow(Double.parseDouble(weatherBean.getWindSpeed()) - Double.parseDouble(forecastBean.getWindSpeed()), 2.0);
			distance[index] = Math.sqrt(distance[index]);
			index++;
		}
		
		// Get the index for last year weather data that have Min Euclidean distance.
		index = 0;
		double min = distance[0];
		for(int i = 1; i < distance.length; i++) {
	           if (distance[i] < min ){
	               min = distance[i];
	               index = i;
	           }
		}
		
		return lastYearWeatherDataBean[index].getSummary();
	}
}
