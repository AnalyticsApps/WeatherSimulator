package com.weather.simulator.connectors;

import java.util.Map;

import com.weather.simulator.dao.WeatherSimulatorBean;
import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Interface for all Connectors.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public interface IConnector {
	
	/**
	 * Create the URL Template for a Rest API provided by external providers.
	 * 
	 * @return
	 */
	public String getURLTemplate();
	
	
	/**
	 * Return the proper URL to connect to external providers and update the place holders for Latitude/Longitude, key etc.
	 * 
	 * @param urlTemplate
	 * @param values
	 * @return
	 */
	public String getURL(String urlTemplate, Map<String, String> values);

	
	/**
	 * Convert the json content provided by external providers to Bean object
	 * 
	 * @param jsonContents
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public WeatherSimulatorBean convertToJSON(String jsonContents) throws WeatherSimulatorException ;
	
	
	/**
	 * Connects to external providers and get the JSON response.
	 * 
	 * @param urlStr
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public String Connector(String urlStr) throws WeatherSimulatorException;

}
