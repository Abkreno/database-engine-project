package com.nan.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	private static FileReader propertiesFile;
	private static Properties properties;
	private static boolean initialized = false;

	public static String readProperty(String property) {
		if (!initialized) {
			init();
		}
		return properties.getProperty(property);
	}

	private static void init() {
		try {
			propertiesFile = new FileReader(new File(FileManager.userDirectory
					+ "/config/DBApp.properties"));
			properties = new Properties();
			properties.load(propertiesFile);
			initialized = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
