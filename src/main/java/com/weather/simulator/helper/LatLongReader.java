package com.weather.simulator.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.weather.simulator.dao.LatLongBean;
import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Read the LatLongCity.list.json that have latitude/longitude for a suburb/city.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class LatLongReader {

	private static final Logger logger = Logger.getLogger(LatLongReader.class);

	/**
	 * Parse the file LatLongCity.list.json and load to LatLongBean
	 * 
	 * @param file
	 * @throws WeatherSimulatorException
	 */
	public Map<String, ArrayList<LatLongBean>> parse(String file) throws WeatherSimulatorException {

		// Map that holds a list of LatLongBean. The key for the map is name(suburb).
		// Same suburb may exist in different countries. So we use list of LatLongBean.
		// If a user provide a suburb that exist in different countries, then the
		// application will display all the suburb to user and request to select one suburb.
		Map<String, ArrayList<LatLongBean>> rowList = new HashMap<String, ArrayList<LatLongBean>>();

		try {

			LatLongBean latLongBean = null;
			int duplicateRecordCount = 0;

			// File LatLongCity.list.json is almost 29 MB, using JSON Stream to parse the huge file.
			JsonParser parser = new JsonFactory().createParser(new File(file));

			// Token represent [ - JsonToken.START_ARRAY
			parser.nextToken();

			// Loop thru the parser, till last token in the file ] - JsonToken.END_ARRAY
			while (parser.currentToken() != null && parser.currentToken() != JsonToken.END_ARRAY) {
				/*
				 * Read the complete object and load to LatLongBean
				 * 
				 * Sample JSON representation. 
				 * { 
				 * 	"id": 7303198, 
				 * 	"name": "Lake Louise",
				 * 	"country": "CA", 
				 * 	"coord": { "lon": -116.28479, "lat": 51.40435 } 
				 * }
				 */
				parser.nextToken(); // Token represent { - JsonToken.START_OBJECT
				
				// Token represent } - JsonToken.END_OBJECT
				while (parser.currentToken() != null && parser.currentToken() != JsonToken.END_OBJECT) { 
					latLongBean = new LatLongBean();
					if ("id".equalsIgnoreCase(parser.nextFieldName())) { // Token represent id - JsonToken.FIELD_NAME
						parser.nextValue(); // Token represent value for field id - JsonToken.VALUE_NUMBER_INT
						latLongBean.setId(parser.getLongValue());
					}
					if ("name".equalsIgnoreCase(parser.nextFieldName())) { // Token represent name -
																			// JsonToken.FIELD_NAME
						parser.nextValue(); // Token represent value for field name - JsonToken.VALUE_STRING
						latLongBean.setName(parser.getText().toLowerCase());
					}
					if ("country".equalsIgnoreCase(parser.nextFieldName())) { // Token represent country -
																				// JsonToken.FIELD_NAME
						parser.nextValue(); // Token represent value for field country - JsonToken.VALUE_STRING
						latLongBean.setCountry(parser.getText().toUpperCase());
					}

					parser.nextToken(); // Token represent coord - JsonToken.FIELD_NAME
					parser.nextToken(); // Token represent { - JsonToken.START_OBJECT
					if ("lon".equalsIgnoreCase(parser.nextFieldName())) { // Token represent lon - JsonToken.FIELD_NAME
						parser.nextValue(); // Token represent value for field lon - JsonToken.VALUE_NUMBER_FLOAT
						latLongBean.setLongitude(parser.getValueAsString());
					}
					if ("lat".equalsIgnoreCase(parser.nextFieldName())) { // Token represent long - JsonToken.FIELD_NAME
						parser.nextValue(); // Token represent value for field lat - JsonToken.VALUE_NUMBER_FLOAT
						latLongBean.setLatitude(parser.getValueAsString());
					}

					parser.nextToken(); // Token represent } - JsonToken.END_OBJECT
					parser.nextToken(); // Token represent } - JsonToken.END_OBJECT

					ArrayList<LatLongBean> latLongList = rowList.get(latLongBean.getName());
					if (latLongList == null) {
						// Create a new list of LatLongBean
						latLongList = new ArrayList<LatLongBean>();
						latLongList.add(latLongBean);
					} else {
						// Add to existing list
						boolean noFound = true;
						for (LatLongBean latLongB : latLongList) {
							if(latLongB.equals(latLongBean)) {
								//logger.info(String.format("Duplicate Entry found : %s", latLongBean.toString()));
								duplicateRecordCount++;
								noFound = false;
								break;
							}
						}
						if(noFound)
							latLongList.add(latLongBean);
					}

					if(latLongBean.getName() != null)
						rowList.put(latLongBean.getName().toLowerCase(), latLongList);

				}

			}
			
			logger.info(String.format("Found %d duplicate records.", duplicateRecordCount));
			
			parser.close();
			
		} catch (IOException e) {
			logger.error(String.format("I/O Exception while parsing the file : %s", file));
			logger.error(e.getMessage());
			throw new WeatherSimulatorException(
					String.format(String.format("I/O Exception while connecting to URL: %s", file)));

		}

		return rowList;
	}

}
