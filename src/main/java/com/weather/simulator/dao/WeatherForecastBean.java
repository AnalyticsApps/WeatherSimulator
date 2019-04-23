package com.weather.simulator.dao;

import com.weather.simulator.utils.WeatherSimulatorConstants;

/**
 * WeatherForcastBean hold predicted weather details for a location.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherForecastBean extends WeatherSimulatorBean {
	
	// Stores the elevation of a location/suburb.
	String elevation;
	
	String date;
	
	// Stores the Suburb/location name.
	String location;
	
	// Human-readable text summary of this data point. 
	String summary;
	
	// Air temperature in degrees Fahrenheit.
	String temperature;

	// Percentage of sky occluded by clouds, between 0 and 1, inclusive..
	String cloudCover;

	// Dew point in degrees Fahrenheit.
	String dewPoint;

	// Relative humidity, between 0 and 1, inclusive.
	String humidity;

	// Sea-level air pressure in millibars.
	String pressure;

	// Wind speed in miles per hour.
	String windSpeed;


	/**
	 * @return the elevation
	 */
	public String getElevation() {
		return elevation;
	}


	/**
	 * @param elevation the elevation to set
	 */
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}


	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}


	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}


	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}


	/**
	 * @return the temperature
	 */
	public String getTemperature() {
		return temperature;
	}


	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}


	/**
	 * @return the cloudCover
	 */
	public String getCloudCover() {
		return cloudCover;
	}


	/**
	 * @param cloudCover the cloudCover to set
	 */
	public void setCloudCover(String cloudCover) {
		this.cloudCover = cloudCover;
	}


	/**
	 * @return the dewPoint
	 */
	public String getDewPoint() {
		return dewPoint;
	}


	/**
	 * @param dewPoint the dewPoint to set
	 */
	public void setDewPoint(String dewPoint) {
		this.dewPoint = dewPoint;
	}


	/**
	 * @return the humidity
	 */
	public String getHumidity() {
		return humidity;
	}


	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}


	/**
	 * @return the pressure
	 */
	public String getPressure() {
		return pressure;
	}


	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(String pressure) {
		this.pressure = pressure;
	}


	/**
	 * @return the windSpeed
	 */
	public String getWindSpeed() {
		return windSpeed;
	}


	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}
	
	 
	@Override
	public String toString() {
		return String.format(WeatherSimulatorConstants.FORCAST_OUTPUT_FORMAT, 
				this.location, this.latitude.concat(",").concat(this.longitude).concat(",").concat(this.elevation), 
				this.date, this.summary, this.temperature, this.cloudCover, this.dewPoint, this.humidity, 
				this.pressure, this.windSpeed);
	}

}
