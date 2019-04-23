package com.weather.simulator.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;


/**
 * Test Cases for testing Elevation API Connector
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class DateUtilTest {

	@Test
	public void getEpochTimeTest() throws Exception {
		/**
		 * Test Case - Convert date (yyyy-MM-dd) to Epoch time.
		 */
		String date = "2019-04-17";
		long actual = 1555423200;
		long epoch = DateUtil.getEpochTime(date, TimeZone.getTimeZone("Australia/Sydney"));
		assertEquals(actual, epoch);		
	}
	
	@Test
	public void getDateTest() throws Exception {
		/**
		 * Test Case - Convert Epoch time to date (yyyy-MM-dd).
		 */
		String actual = "2019-04-17";
		long epoch = 1555423200;
		String date = DateUtil.getDate(epoch, TimeZone.getTimeZone("Australia/Sydney"));
		assertEquals(actual, date);
	}
	
	@Test
	public void getLastNEpochDateFromNowTest() throws Exception {
		/**
		 * Test Case - Return a array of epoch time for last N days from now.
		 */		
		String[] epochArray = DateUtil.getLastNEpochDateFromNow(4, "Australia/Sydney");
		
		ZoneId id = ZoneId.of("Australia/Sydney");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for(int i = 1; i <= 4; i++) {
			LocalDateTime previousDay = LocalDateTime.now().minusDays(i);
			ZonedDateTime zonedDateTime = ZonedDateTime.of(previousDay, id);
			assertEquals(Long.toString(DateUtil.getEpochTime(dateFormat.format(zonedDateTime), TimeZone.getTimeZone("Australia/Sydney"))), epochArray[i-1]);
		}
	}

}
