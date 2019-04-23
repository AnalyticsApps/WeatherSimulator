package com.weather.simulator.connectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import com.weather.simulator.dao.WeatherBean;
import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Test Cases for testing Darksky Weather API Connector
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherAPIConnectorTest {
	
	private String getKey() {
        try (InputStream input = new FileInputStream(new File("src/test/resources/WeatherSimulator.properties").getAbsolutePath())) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("weatherAPI.connector.key");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
	}

	@Test
	public void readMetadataTest1() throws Exception {
		/**
		 * Test Case 1 - API to get the weather for suburb Rooty Hill
		 *
		 * {
		 *     "id": 2151157,
		 *     "name": "Rooty Hill",
		 *     "country": "AU",
		 *     "coord": {
		 *     		"lon": 150.833328,
		 *     		"lat": -33.76667
		 *     }
		 * }
		 * 
		 */
		
		WeatherAPIConnector conn = new WeatherAPIConnector();
		String key = getKey();
		Map<String, String> val = new HashMap<String, String>();
		val.put("key", key);
		val.put("latitude", "-33.76667");
		val.put("longitude", "150.833328");
		String url = conn.getURL(conn.getURLTemplate(), val);
		
		assertEquals("https://api.darksky.net/forecast/" + key + "/-33.76667,150.833328?exclude=minutely,hourly,daily,alerts,flags", url);		
		WeatherBean weatherBean = conn.convertToJSON(conn.Connector(url));
		assertEquals("150.833328", weatherBean.getLongitude());
		assertEquals("-33.76667", weatherBean.getLatitude());
	}
	
	@Test
	public void readMetadataTest2() throws Exception {
		/**
		 * Test Case 2 - Provide an invalid Latitude & longitude.
		 */
		String key = getKey();
		WeatherAPIConnector conn = new WeatherAPIConnector();
		Map<String, String> val = new HashMap<String, String>();
		val.put("key", key);
		val.put("latitude", "1000");
		val.put("longitude", "5000");
		String url = conn.getURL(conn.getURLTemplate(), val);
		
		assertEquals("https://api.darksky.net/forecast/" + key + "/1000,5000?exclude=minutely,hourly,daily,alerts,flags", url);	
		try {
			conn.convertToJSON(conn.Connector(url));
		}catch(WeatherSimulatorException exp) {
			assertEquals(true, exp.getMessage().startsWith(String.format("Failed to connect to URL %s.", url)));
		}
	}

}
