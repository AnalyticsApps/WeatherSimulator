package com.weather.simulator.dao;

/**
 * ElevationBean hold Lat/Long coordinates for a suburb/city.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class LatLongBean extends WeatherSimulatorBean {

	long id;
	
	// Stores Suburb/location
	String name;
	
	// Stores Country name.
	String country;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		StringBuilder retStrBuilder = new StringBuilder();
		retStrBuilder.append("[ id = " + this.id);
		retStrBuilder.append(" , name = " + this.name);
		retStrBuilder.append(" , country = " + this.country);
		retStrBuilder.append(" , longitude = " + this.longitude);
		retStrBuilder.append(" , latitude = " + this.latitude + " ]");
		return retStrBuilder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LatLongBean latLongBean = (LatLongBean) o;
		// If suburb and country is same then we mark the object is equal.
		return name.equals(latLongBean.name) && country.equals(latLongBean.country);
	}
}
