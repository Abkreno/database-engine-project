package com.nan.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import com.nan.utils.FileManager;

public class Schema {
	private static Hashtable<String, Hashtable<String, Column>> schema;

	public Schema() {
		try {
			schema = FileManager.readSchema();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print() {
		Iterator<String> tableIt = schema.keySet().iterator();
		while (tableIt.hasNext()) {
			String tableName = tableIt.next();
			System.out.println(tableName + " Columns:");
			Hashtable<String, Column> currColumns = schema.get(tableName);
			Iterator<String> columnIt = currColumns.keySet().iterator();
			while (columnIt.hasNext()) {
				System.out.println(currColumns.get(columnIt.next()));
			}
			System.out.println("-----------------------");
		}
	}

	public static boolean checkTableExist(String tableName) {
		return schema.containsKey(tableName);
	}

	public static boolean checkIndexExist(String tableName, String colName) {
		return schema.get(tableName).get(colName).isIndexed();
	}

	public static boolean checkColExist(String tableName, String colName) {
		return schema.get(tableName).containsKey(colName);
	}

	public static boolean checkMultiIndexExist(String tableName,
			String colName1, String colName2) {
		return schema.get(tableName).get(colName1).isIndexed()
				&& schema.get(tableName).get(colName2).isIndexed();
	}
}
