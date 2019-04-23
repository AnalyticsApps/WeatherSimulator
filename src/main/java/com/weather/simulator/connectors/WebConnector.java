package com.weather.simulator.connectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.weather.simulator.exception.WeatherSimulatorException;

/**
 * Abstract class for IConnector
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public abstract class WebConnector implements IConnector {

	private static final Logger logger = Logger.getLogger(WebConnector.class);

	/**
	 * Connects to external providers and get the JSON response.
	 * 
	 * @param urlStr
	 * @return
	 * @throws WeatherSimulatorException
	 */
	public String Connector(String urlStr) throws WeatherSimulatorException {
		HttpURLConnection conn = null;
		String contents = "";
		try {
			logger.info(String.format("Connecting to URL: %s", urlStr));
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				logger.error(String.format("Failed to connect to URL: %s HTTP error code : %d", urlStr,
						conn.getResponseCode()));
				logger.error(retreiveContent(conn.getErrorStream()));
				throw new WeatherSimulatorException(
						String.format("Failed to connect to URL %s. Refer the logs for more details.", urlStr));
			}

			contents = retreiveContent(conn.getInputStream());

		} catch (IOException e) {
			logger.error(String.format("I/O Exception while connecting to URL: %s", urlStr));
			logger.error(e.getMessage());
			throw new WeatherSimulatorException(
					String.format(String.format("I/O Exception while connecting to URL: %s", urlStr)));

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		logger.info("Response : " + contents);
		return contents;
	}

	/**
	 * Retrieve the content from HTTP Stream.
	 * 
	 * @param iStream
	 * @return
	 * @throws IOException
	 */
	private String retreiveContent(InputStream iStream) throws IOException {
		StringBuilder contents = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader((iStream)));
		String resp = "";
		while ((resp = br.readLine()) != null) {
			contents.append(resp);
		}
		return contents.toString();
	}
}
