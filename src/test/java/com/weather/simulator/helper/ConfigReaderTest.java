package com.weather.simulator.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Test Cases for testing WeatherSimulator.properties
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class ConfigReaderTest {

	@Test
	public void getPropertyTest() throws Exception {
		/**
		 * Test Case - Test the property
		 */		
		assertEquals("/opt/WeatherSimulator/conf/LatLongCity.list.json", ConfigReader.getProperty("weatherAPI.latitudeLongitude.file"));		
	}
	
}
