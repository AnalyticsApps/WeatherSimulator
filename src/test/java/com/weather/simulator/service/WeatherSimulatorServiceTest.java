package com.weather.simulator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.weather.simulator.dao.LatLongBean;
import com.weather.simulator.dao.WeatherForecastBean;


/**
 * Test Cases for predicting the weather.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherSimulatorServiceTest {

	@Test
	public void processTest() throws Exception {
		/**
		 * Test Case - Process the LatLongBean and return the weather forecast for that data point. 
		 */		
		
		LatLongBean latLongBean = new LatLongBean();
		latLongBean.setCountry("AU");
		latLongBean.setId(2143973);
		latLongBean.setName("Westmead");
		latLongBean.setLatitude("-33.803829");
		latLongBean.setLongitude("150.987686");
		
		WeatherSimulatorService inst = new WeatherSimulatorService();
		WeatherForecastBean forecastBean = inst.process(latLongBean);
		
		assertEquals("Westmead", forecastBean.getLocation());
		assertEquals("-33.803829", forecastBean.getLatitude());
		assertEquals("150.987686", forecastBean.getLongitude());
		assertEquals("34", forecastBean.getElevation());

		  
	}
	
}
