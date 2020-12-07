package utilities;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ConfigFileReader {
	private Properties properties;
	private final String propertyFilePath = "src\\test\\resources\\Configuration\\ConfigFile.properties";	

	/**
	 * Set the config file to be read
	 */
	public ConfigFileReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));			
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}

	/**
	 * Get the value for the selected property and return it in string
	 * 
	 * @param propertyName
	 * @return
	 */
	public String getStringPropery(String propertyName) {
		String propertyValue = properties.getProperty(propertyName);
		if (propertyValue != null)
			return propertyValue;
		else
			throw new RuntimeException(
					"Property " + propertyName + " not specified in the Configuration.properties file.");
	}

	/**
	 * Get the value for the selected property and return it in Integer
	 * 
	 * @param propertyName
	 * @return
	 */
	public int getIntPropery(String propertyName) {
		String propertyValue = properties.getProperty(propertyName);
		if (propertyValue != null)
			return Integer.parseInt(propertyValue);
		else
			throw new RuntimeException(
					"Property " + propertyName + " not specified in the Configuration.properties file.");
	}

	/**
	 * Get the value for the selected property and return it in Long
	 * 
	 * @param propertyName
	 * @return
	 */
	public long getLongPropery(String propertyName) {
		String propertyValue = properties.getProperty(propertyName);
		if (propertyValue != null)
			return Long.parseLong(propertyValue);
		else
			throw new RuntimeException(
					"Property " + propertyName + " not specified in the Configuration.properties file.");
	}

	/**
	 * Get the absolute path of the selenium driver needed, acording the chosen
	 * browser
	 * 
	 * @return
	 */
	public String getDriverPath() {
		String DriverPath = properties.getProperty("DriversRootDirectory");
		switch (properties.getProperty("Browser")) {
		case "FF":
			if (properties.getProperty("BrowserArchitecture").equals("64"))
				DriverPath = DriverPath + properties.getProperty("FF64DriverPath");
			else
				DriverPath = DriverPath + properties.getProperty("FFDriverPath");
			break;
		case "IE":
			if (properties.getProperty("BrowserArchitecture").equals("64"))
				DriverPath = DriverPath + properties.getProperty("IE64DriverPath");
			else
				DriverPath = DriverPath + properties.getProperty("IEDriverPath");
			break;
		case "CH":
			DriverPath = DriverPath + properties.getProperty("CHDriverPath");
			break;
		default:
			DriverPath = DriverPath + properties.getProperty("FFDriverPath");
			break;
		}
		if (!DriverPath.isEmpty())
			return DriverPath;
		else
			throw new RuntimeException("Driver Path is not specified in the Configuration.properties file.");
	}

}
