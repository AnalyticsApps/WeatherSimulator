package com.weather.simulator.connectors;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.simulator.dao.ElevationBean;
import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Connects to elevation-api.io provider to get the elevation of a Latitude/Longitude coordinate.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class ElevationAPIConnector extends WebConnector {

	private static final Logger logger = Logger.getLogger(ElevationAPIConnector.class);
	
	String[] urlPlaceHolder = new String[] { "latitude_placeHolder", "longitude_placeHolder" };

	/**
	 * Create the URL template based on key.
	 * 
	 * @param key
	 * @return
	 */
	public String getURLTemplate() {
		return "https://elevation-api.io/api/elevation?points=(" + urlPlaceHolder[0] + ","
				+ urlPlaceHolder[1] + ")";
	}

	
	/**
	 * Create the complete URL based on user input.
	 * 
	 * @param urlTemplate
	 * @param values
	 * @return
	 */
	public String getURL(String urlTemplate, Map<String, String> values) {
		return urlTemplate.replaceFirst( urlPlaceHolder[0], values.get("latitude"))
				.replaceFirst( urlPlaceHolder[1], values.get("longitude"));
	}

	
	/**
	 * Convert the json content provided by external providers to Bean object
	 * 
	 * @param jsonContents
	 * @return
	 * @throws WeatherSimulatorException
	 */
	 @SuppressWarnings("unchecked")
	public ElevationBean convertToJSON(String jsonContents) throws WeatherSimulatorException {
		 
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNodeRoot = null;
		try {
			jsonNodeRoot = mapper.readTree(jsonContents);
		} catch (IOException e) {
			logger.error(String.format("I/O Exception while parsing the JSON content: %s", e.getMessage()));
			logger.error(e.getMessage());
			throw new WeatherSimulatorException(
					String.format("I/O Exception while parsing the JSON content: %s", e.getMessage()));
		}
		JsonNode jsonNodeYear = jsonNodeRoot.get("elevations");
		Map<String, Double> resultMap = mapper.convertValue(jsonNodeYear.get(0), Map.class);
		ElevationBean bean = new ElevationBean();
		bean.setLatitude(resultMap.get("lat").toString());
		bean.setLongitude(resultMap.get("lon").toString());
		bean.setElevation(String.valueOf(resultMap.get("elevation").intValue()));
		return bean;
	}

}
