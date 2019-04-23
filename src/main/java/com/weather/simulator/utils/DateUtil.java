package com.weather.simulator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Utility to convert date to different format.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class DateUtil {

	private static final Logger logger = Logger.getLogger(DateUtil.class);

	/**
	 * Convert date (yyyy-MM-dd) for a timezone to Epoch time.
	 * 
	 * @param date
	 * @param tz
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public static long getEpochTime(String date, TimeZone tz) throws WeatherSimulatorException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(tz);
		Date dt = null;
		try {
			dt = dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error(String.format("Date parsing error for date: %s timezone: %s", date, tz.getDisplayName()));
			logger.error(e.getMessage());
			throw new WeatherSimulatorException(String
					.format(String.format("Date parsing error for date: %s timezone: %s", date, tz.getDisplayName())));
		}
		long epoch = dt.getTime();
		epoch = (long) (epoch / 1000L);
		logger.info(
				String.format("Date: %s timezone: %s converted to epoch time: %d", date, tz.getDisplayName(), epoch));
		return epoch;
	}

	
	/**
	 * Convert epoch time to date format(yyyy-MM-dd) for a timezone.
	 * 
	 * @param epoch
	 * @param tz
	 * @return
	 */
	public static String getDate(long epoch, TimeZone tz) {
		Date date = new Date(epoch * 1000L);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setTimeZone(tz);
		String newDate = dateFormat.format(date);
		logger.info(String.format("Epoch: %d timezone: %s converted to date: %s", epoch, tz.getDisplayName(), newDate));
		return newDate;
	}

	
	/**
	 * Return a array of epoch time for last N days from now.
	 * 
	 * @param lastNDays
	 * @param timezone
	 * @return
	 */
	public static String[] getLastNEpochDateFromNow(int lastNDays, String timezone) throws WeatherSimulatorException {
		String[] epoch = new String[lastNDays];
		ZoneId id = ZoneId.of(timezone);
		TimeZone tz = TimeZone.getTimeZone(timezone);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int index = 0;
		for (int lastday = 1; index < lastNDays; lastday++) {
			LocalDateTime previousDay = LocalDateTime.now().minusDays(lastday);
			ZonedDateTime zonedDateTime = ZonedDateTime.of(previousDay, id);
			epoch[index] = Long.toString(getEpochTime(zonedDateTime.format(dateFormat),tz));
			index++;
		}
		logger.info(String.format("Last N Days epoch time: %s", String.valueOf(epoch)));
		return epoch;
	}
	
	
	/**
	 * Return a array of epoch time for last or next N days on last year.
	 * 
	 * @param lastOrNextNDays
	 * @param timezone
	 * @return
	 */
	public static String[] getNEpochDatesOnLastYear(int lastOrNextNDays, String timezone) throws WeatherSimulatorException {
		String[] epoch = null;
		ZoneId id = ZoneId.of(timezone);
		TimeZone tz = TimeZone.getTimeZone(timezone);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		int index = 0;
		if(lastOrNextNDays == 0) {
			// Get the epoch time for last year current date.
			epoch = new String[1];
			LocalDateTime previousDay = LocalDateTime.now().minusYears(1);
			ZonedDateTime zonedDateTime = ZonedDateTime.of(previousDay, id);
			epoch[index] = Long.toString(getEpochTime(zonedDateTime.format(dateFormat),tz));
		}else if(lastOrNextNDays < 0) {
			// Get the epoch dates for last year, previous N days.
			lastOrNextNDays = Math.abs(lastOrNextNDays); 
			epoch = new String[lastOrNextNDays];
			for (int lastday = lastOrNextNDays; lastday >= 1; lastday--) {
				LocalDateTime previousDay = LocalDateTime.now().minusYears(1).minusDays(lastday);
				ZonedDateTime zonedDateTime = ZonedDateTime.of(previousDay, id);
				epoch[index] = Long.toString(getEpochTime(zonedDateTime.format(dateFormat),tz));
				index++;
			}

		}else {
			// Get the epoch dates for last year, next N days
			epoch = new String[lastOrNextNDays];
			for (int lastday = 1; lastday <= lastOrNextNDays; lastday++) {
				LocalDateTime nextDay = LocalDateTime.now().minusYears(1).plusDays(lastday);
				ZonedDateTime zonedDateTime = ZonedDateTime.of(nextDay, id);
				epoch[index] = Long.toString(getEpochTime(zonedDateTime.format(dateFormat),tz));
				index++;
			}
			
		}
		logger.info(String.format("LastN or NextN Days epoch time: %s", String.valueOf(epoch)));
		return epoch;
	}

}
