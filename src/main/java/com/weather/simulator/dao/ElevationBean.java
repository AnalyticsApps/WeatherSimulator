package com.weather.simulator.dao;

/**
 * ElevationBean hold elevation for a particular Lat/Long coordinates.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class ElevationBean extends WeatherSimulatorBean {

	// Stores the elevation of a location/suburb.
	String elevation;
	
	// Stores the Suburb/location name.
	String suburb;

	/**
	 * @return elevation
	 */
	public String getElevation() {
		return elevation;
	}

	/**
	 * @param elevation
	 *            the elevation to set
	 */
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}

	/**
	 * @return the suburb
	 */
	public String getSuburb() {
		return suburb;
	}

	/**
	 * @param suburb the suburb to set
	 */
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	
	@Override
	public String toString() {
		StringBuilder retStrBuilder = new StringBuilder();
		retStrBuilder.append("[ latitude = " + this.latitude);
		retStrBuilder.append(" , longitude = " + this.longitude);
		retStrBuilder.append(" , Suburb = " + this.suburb);
		retStrBuilder.append(" , elevation = " + this.elevation + " ]");
		return retStrBuilder.toString();
	}

	
}
