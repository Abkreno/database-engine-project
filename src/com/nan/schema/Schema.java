package com.nan.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

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

	public void print() {
		Iterator it = schema.keySet().iterator();
		while (it.hasNext()) {
			String tableName = (String) it.next();
			System.out.println(tableName + " Columns:");
			ArrayList<Column> currColumns = schema.get(tableName);
			for (Column curr : currColumns) {
				System.out.println(curr);
			}
			System.out.println("-----------------------");
		}
	}

	public static boolean checkTableExist(String tableName) {
		return schema.containsKey(tableName);
	}

}
