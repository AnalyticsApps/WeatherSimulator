package com.weather.simulator.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.weather.simulator.dao.LatLongBean;


/**
 * Test Cases for testing the Lat/Long reader file conf/LatLongCity.list.json
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class LatLongReaderTest {

	@Test
	public void parseTest() throws Exception {
		/**
		 * Test Case - Load the file and check the lat/long details.
		 */		
		
		String latLongPath = new File("src/test/resources/LatLongCity.list.json").getAbsolutePath();
		LatLongReader reader= new LatLongReader();
		Map<String, ArrayList<LatLongBean>> result = reader.parse(latLongPath);
		
		LatLongBean westmeadBean = result.get("westmead").get(0);
		assertEquals(2143973, westmeadBean.getId());
		assertEquals("westmead", westmeadBean.getName());
		assertEquals("AU", westmeadBean.getCountry());
		assertEquals("150.987686", westmeadBean.getLongitude());
		assertEquals("-33.803829", westmeadBean.getLatitude());
		
		long index = 0;
		for (ArrayList<LatLongBean> latLongList : result.values()) {
			index += latLongList.size();	
		}
		assertEquals(168777, index);
		  
	}
	
}
