package com.weather.simulator.connectors;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.simulator.dao.WeatherBean;
import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Connects to Darksky Weather API to get the current weather & Historical
 * weather.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherAPIConnector extends WebConnector {

	private static final Logger logger = Logger.getLogger(WeatherAPIConnector.class);

	String[] urlPlaceHolder = new String[] { "key_placeHolder", "latitude_placeHolder", "longitude_placeHolder",
			"date_placeHolder" };

	/**
	 * Create the URL template based on key.
	 * 
	 * @param key
	 * @return
	 */
	public String getURLTemplate() {
		return "https://api.darksky.net/forecast/" + urlPlaceHolder[0] + "/" + urlPlaceHolder[1] + ","
				+ urlPlaceHolder[2] + "," + urlPlaceHolder[3] + "?exclude=minutely,hourly,daily,alerts,flags";
	}

	
	/**
	 * Create the complete URL based on user input.
	 * 
	 * @param urlTemplate
	 * @param values
	 * @return
	 */
	public String getURL(String urlTemplate, Map<String, String> values) {
		// Replace the place holder with actual values.
		urlTemplate = urlTemplate.replaceFirst(urlPlaceHolder[0], values.get("key"))
				.replaceFirst(urlPlaceHolder[1], values.get("latitude"))
				.replaceFirst(urlPlaceHolder[2], values.get("longitude"));

		// Date is used to get the historical weather data.
		String time = values.get("date");
		if (time != null) {
			// Get the historical weather for a particular date.
			urlTemplate = urlTemplate.replaceFirst(urlPlaceHolder[3], values.get("date"));
		} else {
			// Get the current weather.
			urlTemplate = urlTemplate.replaceFirst(",date_placeHolder", "");
		}

		return urlTemplate;
	}

	
	/**
	 * Convert the json content provided by external providers to Bean object
	 * 
	 * @param jsonContents
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public WeatherBean convertToJSON(String jsonContents) throws WeatherSimulatorException {
		WeatherBean weatherBean = new WeatherBean();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		JsonNode jsonNode = null;
		try {
			jsonNode = mapper.readTree(jsonContents);
		} catch (IOException e) {
			logger.error(String.format("I/O Exception while parsing the JSON content: %s", e.getMessage()));
			logger.error(e.getMessage());
			throw new WeatherSimulatorException(
					String.format("I/O Exception while parsing the JSON content: %s", e.getMessage()));
		}
		weatherBean.setLatitude(jsonNode.get("latitude").asText());
		weatherBean.setLongitude(jsonNode.get("longitude").asText());
		weatherBean.setTimezone(jsonNode.get("timezone").asText());

		jsonNode = jsonNode.get("currently");
		weatherBean.setTime(jsonNode.get("time").asText());
		weatherBean.setSummary(jsonNode.get("summary").asText());
		weatherBean.setTemperature(jsonNode.get("temperature").asText());
		weatherBean.setCloudCover(jsonNode.get("cloudCover").asText());
		weatherBean.setDewPoint(jsonNode.get("dewPoint").asText());
		weatherBean.setHumidity(jsonNode.get("humidity").asText());
		weatherBean.setPressure(jsonNode.get("pressure").asText());
		weatherBean.setWindSpeed(jsonNode.get("windSpeed").asText());
		
		logger.info(String.format("Contents in WeatherBean :", weatherBean.toString()));

		return weatherBean;
	}

}
