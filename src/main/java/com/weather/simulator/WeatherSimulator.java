package com.weather.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.weather.simulator.dao.LatLongBean;
import com.weather.simulator.dao.WeatherForecastBean;
import com.weather.simulator.exception.WeatherSimulatorException;
import com.weather.simulator.helper.ConfigReader;
import com.weather.simulator.helper.LatLongReader;
import com.weather.simulator.service.IWeatherSimulatorService;
import com.weather.simulator.service.WeatherSimulatorService;
import com.weather.simulator.utils.DisplayUtil;
import com.weather.simulator.utils.WeatherSimulatorConstants;

/**
 * Starting point of the application. Get the input and initiate the Weather Simulation App.
 * 
 * @author Nisanth Simon
 * @version 1.0
 */
public class WeatherSimulator {

	private static final Logger logger = Logger.getLogger(WeatherSimulator.class);

	public static void main(String[] args) throws WeatherSimulatorException {
		
		logger.info(" Initiating the Weather Simulator App");
		
		// Load the mapping file (LatLongCity.list.json) that have from location to lat/long
		LatLongReader latLongReader = new LatLongReader();
		Map<String, ArrayList<LatLongBean>> suburbList = latLongReader.parse(ConfigReader.getProperty(WeatherSimulatorConstants.LAT_LONG_FILE));
		
		// Get the user input
		List<LatLongBean> latLongUserList = new ArrayList<LatLongBean>();
		Scanner in = new Scanner(System.in);		
		userInput(suburbList, latLongUserList, in);
		in.close();
		
		System.out.println("\n\n Processing the Weather simulation \n\n");
		boolean addHeader = true;
		StringBuilder outFile = new StringBuilder();
		IWeatherSimulatorService service = new WeatherSimulatorService();
		for (LatLongBean latLongBean : latLongUserList) {
			logger.info(String.format(" Processing the location: %s", latLongBean.getName()));
			WeatherForecastBean forecastBean = service.process(latLongBean);
			if(addHeader) {
				// Add header while printing to console.
				DisplayUtil.print(forecastBean, addHeader);
				addHeader = false;
			}else {
				outFile.append(forecastBean.toString().replaceAll("\\s+","|")).append("\n");
				DisplayUtil.print(forecastBean, addHeader);
			}

		}
		
		// Write the weather details to {APP_HOME}/output/weatherResults{DATE_TIIME}.txt
		String fileName = DisplayUtil.write(outFile.toString());
		
		System.out.println(String.format("\n\n\n\n Weather output written to %s \n\n", fileName));
		
		System.out.println("\n\n File conversion completed. \n\n");

	}

	/**
	 * Get the user input for multiple location.
	 * 
	 * @param suburbList
	 * @param latLongUserList
	 * @param in
	 */
	private static void userInput(Map<String, ArrayList<LatLongBean>> suburbList, List<LatLongBean> latLongUserList,
			Scanner in) {
		String addMoreLoc = "";
		do {
			System.out.print("\n\n Enter the City/Suburb: ");
			String suburb = in.nextLine();
			
			logger.info(String.format(" Suburb entered by user: %s", suburb));
			
	
			ArrayList<LatLongBean> latLongBeanList = suburbList.get(suburb.toLowerCase());
			if(latLongBeanList == null) {
				logger.info("Suburb not found. Add the suburb latitude/longitude details in /opt/WeatherSimulator/conf/LatLongCity.list.json.");
				System.out.println("\n\n Suburb not found. Add the suburb latitude/longitude details in /opt/WeatherSimulator/conf/LatLongCity.list.json.");
			}else if(latLongBeanList.size() > 1) {
				System.out.println("\n\n Multiple Suburbs exist in same name. ");
				DisplayUtil.print(latLongBeanList);
				System.out.print("\n\n Select the suburb you would like to get the weather details: ");
				int indexInp = Integer.parseInt(in.nextLine());
				latLongUserList.add(latLongBeanList.get(indexInp-1));
			}else {
				latLongUserList.add(latLongBeanList.get(0));
			}
			System.out.print("\n\n Would you like to add more location (y/n): ");
			addMoreLoc = in.nextLine();
		}while(addMoreLoc.equalsIgnoreCase("y"));
	}
	

}
