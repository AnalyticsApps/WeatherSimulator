package com.weather.simulator.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utility to read property file WeatherSimulator.properties.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class ConfigReader {

	private static final Logger logger = Logger.getLogger(ConfigReader.class);
	
	private static Properties weatherProperty = new Properties();

	static {
		try {
			String propertyFileName = System.getProperty("WeatherSimulator.properties");
			InputStream fin = new FileInputStream(new File(propertyFileName));
			weatherProperty.load(fin);
		} catch (IOException e) {
			logger.error(String.format("Exception in reading the property - WeatherSimulator.properties. Message: %s", e.getMessage()));
			logger.error(e.getMessage());
			throw new RuntimeException("Exception in reading the property - WeatherSimulator.properties");
		}
	}

	/**
	 * Returns the values from WeatherSimulator.properties
	 * 
	 * @param key
	 * @return value from property file.
	 */
	public static String getProperty(String key) {
		return weatherProperty.getProperty(key);
	}

}
