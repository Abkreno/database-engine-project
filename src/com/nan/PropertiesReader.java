package com.nan;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	private FileReader propertiesFile;
	private Properties properties;

	public PropertiesReader() throws IOException {
		propertiesFile = new FileReader(new File(System.getProperty("user.dir")
				+ "/config/DBApp.properties"));
		properties = new Properties();
		properties.load(propertiesFile);
	}

	public int getMaximumRowsCountinPage() {
		return Integer.parseInt(properties
				.getProperty("MaximumRowsCountinPage"));
	}

	public int getBPlusTreeN() {
		return Integer.parseInt(properties.getProperty("BPlusTreeN"));
	}
}
