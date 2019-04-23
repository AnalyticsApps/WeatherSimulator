package com.weather.simulator.dao;

/**
 * Base Bean Object to hold data retrieved from JSON.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherSimulatorBean {

	String longitude;
	String latitude;
	
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
