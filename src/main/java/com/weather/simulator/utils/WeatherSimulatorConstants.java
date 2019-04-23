package com.weather.simulator.utils;

/**
 * Constants used in Weather Simulator.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherSimulatorConstants {

	// keys for WeatherSimulator.properties
	public static final String LAT_LONG_FILE = "weatherAPI.latitudeLongitude.file";
	public static final String WEATHER_CONNECTOR_KEY = "weatherAPI.connector.key";
	public static final String LAST_N_DAYS_WEATHER = "weatherAPI.lastNDaysWeather";
	public static final String APP_HOME = "weatherAPI.appHome";
	
	// Display the predicted weather in particular format.
	public static final String FORCAST_OUTPUT_FORMAT = "%1$-25s %2$-30s %3$-20s %4$-25s %5$-13s %6$-12s %7$-11s %8$-10s %9$-10s %10$-11s";

}
