package com.nan.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

	private static FileReader propertiesFile;
	private static Properties properties;
	private static boolean initialized = false;
	public static final int MAX_ROW_COUNT_IN_PAGE = readPropertyIntValue("MaximumRowsCountinPage");
	public static final int B_PLUS_TREE_N = readPropertyIntValue("BPlusTreeN");
	public static final float LHT_LOAD_FACTOR = readPropertyFloatValue("LHTLoadFactor");
	public static final int LHT_BUCKET_SIZE = readPropertyIntValue("LHTBucketSize");

	public static String readProperty(String property) {
		if (!initialized) {
			init();
		}
		return properties.getProperty(property);
	}

	public static int readPropertyIntValue(String property) {
		return Integer.parseInt(readProperty(property));
	}

	public static float readPropertyFloatValue(String property) {
		return Float.parseFloat(readProperty(property));
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
