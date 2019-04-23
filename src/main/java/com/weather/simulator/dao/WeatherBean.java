package com.weather.simulator.dao;

/**
 * WeatherBean hold weather data from external provider for a suburb/city.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherBean extends WeatherSimulatorBean {

	// Timezone for the a latitude/longitude datapoint.
	String timezone;
	
	// Local epoch time, when the weather details are taken.
	String time;
	
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
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone
	 *            the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
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
	 * @param temperature
	 *            the temperature to set
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
	 * @param cloudCover
	 *            the cloudCover to set
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
	 * @param dewPoint
	 *            the dewPoint to set
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
	 * @param humidity
	 *            the humidity to set
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
	 * @param pressure
	 *            the pressure to set
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
	 * @param windSpeed
	 *            the windSpeed to set
	 */
	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	
	@Override
	public String toString() {
		StringBuilder retStrBuilder = new StringBuilder();
		retStrBuilder.append("[ latitude = " + this.latitude);
		retStrBuilder.append(" , longitude = " + this.longitude);
		retStrBuilder.append(" , timezone = " + this.timezone);
		retStrBuilder.append(" , time = " + this.time);
		retStrBuilder.append(" , summary = " + this.summary);
		retStrBuilder.append(" , temperature = " + this.temperature);
		retStrBuilder.append(" , cloudCover = " + this.cloudCover);
		retStrBuilder.append(" , dewPoint = " + this.dewPoint);
		retStrBuilder.append(" , humidity = " + this.humidity);
		retStrBuilder.append(" , pressure = " + this.pressure);
		retStrBuilder.append(" , windSpeed = " + this.windSpeed + " ]");
		return retStrBuilder.toString();
	}

}
