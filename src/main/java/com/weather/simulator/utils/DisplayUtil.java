package com.weather.simulator.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.weather.simulator.dao.LatLongBean;
import com.weather.simulator.dao.WeatherForecastBean;
import com.weather.simulator.exception.WeatherSimulatorException;
import com.weather.simulator.helper.ConfigReader;
import com.weather.simulator.helper.LatLongReader;

/**
 * Utility to print formated data to console.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class DisplayUtil {
	
	private static final Logger logger = Logger.getLogger(LatLongReader.class);

	private static final String NEWLINE = "\n";
	
	
	/**
	 * Print the Lat/Long Details to user to get the input of a location/suburb.
	 * 
	 * @param latLongBeanList
	 */
	public static void print(ArrayList<LatLongBean> latLongBeanList) {
		int index = 1;
		StringBuilder builder = new StringBuilder();
		builder.append(String.format(" %1$-6s %2$-40s  %3$-20s", "Id", "Suburb", "Country"));
		builder.append(NEWLINE);
		builder.append(String.format(" %1$-6s %2$-40s  %3$-20s", " ", " ", " ").replaceAll(" ", "="));
		builder.append(NEWLINE);
		for (LatLongBean latLongBean : latLongBeanList) {
			builder.append(String.format(" %1$-6d %2$-40s  %3$-20s", index, latLongBean.getName(), latLongBean.getCountry()));
			builder.append(NEWLINE);
			index++;
		}
		System.out.println(builder.toString());
	}
	
	/**
	 * Print the weather forecast to user.
	 * 
	 * @param forcastBean
	 */
	public static void print(WeatherForecastBean forcastBean, boolean addHeader) {
		if(addHeader) {
			String header = String.format(WeatherSimulatorConstants.FORCAST_OUTPUT_FORMAT, 
					"Location", "Position", "Local Time", "Conditions",
					"Temperature", "Cloud Cover", "Dew Point", "Humidity", "Pressure", "Wind Speed");
			
			System.out.print(NEWLINE);
			System.out.println(header);
			
			String seperator = String.format(WeatherSimulatorConstants.FORCAST_OUTPUT_FORMAT, 
					" ", " ", " ", " ", " ", " ", " ", " ", " ", " ").replace(" ", "-");
			System.out.println(seperator);
		}
		System.out.println(forcastBean);
	}
	
	/**
	 * Write the weather output to a file.
	 * 
	 * @param output
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public static String write(String output) throws WeatherSimulatorException {
		BufferedWriter writer = null;
		String fileappender = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		String file = ConfigReader.getProperty(WeatherSimulatorConstants.APP_HOME) + "output/weatherResults" + fileappender + ".txt";
		try {
			writer = new BufferedWriter(new FileWriter(file, false));
			writer.write("Location|Position|Local Time|Conditions|Temperature|Cloud Cover|Dew Point|Humidity|Pressure|Wind Speed" + NEWLINE);
			writer.write(output);
			writer.flush();
			writer.close();
		}catch(IOException e) {
			logger.error(String.format("I/O Exception while writing the file: %s", file));
			logger.error(e.getMessage());
			throw new WeatherSimulatorException(
					String.format(String.format("I/O Exception while writing the file: %s", file)));
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (IOException e) {}
			}
		}
		return file;
	}
}
