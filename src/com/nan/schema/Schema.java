package com.nan.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.nan.utils.FileManager;

public class Schema {
	private static Hashtable<String, ArrayList<Column>> schema;
	
	public Schema() {
		try {
			schema = FileManager.readSchema();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static boolean checkTableExist(String tableName) {
		return schema.containsKey(tableName);
	}

}
