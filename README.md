# Weather Simulator

**Weather-Simulator** is a Java based a tool to predict weather for a location.

## Approach
The tool predicts the weather data for a given location. Internally, tool retrieves lat/long coordinates from a configuration file(LatLongCity.list.json) then call the Rest API's provided by third parties to get the weather data & elevation for a data location. Based on the recent and historical weather data, the tool predicts the weather.

## Technology Stack
* Java 8
* Maven
* JUnit
* RPM

## Usage Instructions
1. Build using maven 
    ```mvn clean compile package -DWeatherSimulator.properties=src/test/resources/WeatherSimulator.properties```

	Build will compile, test and generate the RPM and final RPM is copied to [RPM Directory](RPM/)
	
2. Install RPM
Click here to download the [RPM](https://github.com/AnalyticsApps/WeatherSimulator/raw/master/RPM/WeatherSimulator-1.0-1.noarch.rpm)
    ```
    [root@hdp3test3 ~]# rpm -ivh WeatherSimulator-1.0-1.noarch.rpm
	Preparing...                          ################################# [100%]
	Updating / installing...
	1:WeatherSimulator-1.0-1           ################################# [100%]
	[root@hdp3test3 ~]#

    
    ```
    ![](image/1_Install.png)

<br> 