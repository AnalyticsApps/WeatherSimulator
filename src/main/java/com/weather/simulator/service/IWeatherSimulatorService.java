package com.weather.simulator.service;

import com.weather.simulator.dao.LatLongBean;
import com.weather.simulator.dao.WeatherForecastBean;
import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Interface for all Weather Simulator Service.
 * 
 * @author Nisanth
 *
 */
public interface IWeatherSimulatorService {
	
	/**
	 * Process the LatLongBean and return the weather forecast for that data point. 
	 * 
	 * @param latLongBean
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public WeatherForecastBean process(LatLongBean latLongBean) throws WeatherSimulatorException;
}
