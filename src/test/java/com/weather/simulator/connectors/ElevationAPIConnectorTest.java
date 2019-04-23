package com.weather.simulator.connectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.weather.simulator.dao.ElevationBean;


/**
 * Test Cases for testing Elevation API Connector
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class ElevationAPIConnectorTest {

	@Test
	public void readMetadataTest1() throws Exception {
		/**
		 * Test Case 1 - API to get the elevation for the suburb Rooty Hill
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
		String lat = "-33.76667";
		String longitude = "150.833328";
		ElevationAPIConnector conn = new ElevationAPIConnector();
		ElevationBean bean = conn.convertToJSON(conn.Connector("https://elevation-api.io/api/elevation?points=(" + lat + "," + longitude + ")"));
		assertEquals(longitude, bean.getLongitude());
		assertEquals(lat, bean.getLatitude());
		
	}

}
