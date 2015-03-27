package com.nan.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.nan.schema.Column;
import com.nan.schema.Table;

public class FileManager {
	protected static String userDirectory = System.getProperty("user.dir");

	public static Hashtable<String, Hashtable<String, Column>> readSchema()
			throws IOException {
		Hashtable<String, Hashtable<String, Column>> schema = new Hashtable<String, Hashtable<String, Column>>();
		File schemaFile = new File(userDirectory + "/data/metadata.csv");
		BufferedReader bf = new BufferedReader(new FileReader(schemaFile));
		String line;
		while ((line = bf.readLine()) != null) {
			String[] data = line.split(", ");
			String tableName = data[0];
			if (!schema.containsKey(tableName))
				schema.put(tableName, new Hashtable<String, Column>());
			Hashtable<String, Column> currTableColumns = schema.get(tableName);
			Column currColumn = new Column(data[1], data[2],
					data[3].equals("True"), data[4].equals("True"), data[5]);
			currTableColumns.put(data[1], currColumn);
		}
		bf.close();
		return schema;
	}

	public static Table readTable(String tableName) {
		return null;
	}
}
