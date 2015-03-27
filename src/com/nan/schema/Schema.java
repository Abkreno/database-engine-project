package com.nan.schema;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import com.nan.utils.FileManager;

public class Schema {
	private static Hashtable<String, Hashtable<String, Column>> schema;

	public Schema() {
		init();
	}

	public static void init() {
		try {
			schema = FileManager.readSchema();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void print() {
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
			Hashtable<String, String> colNames) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * return the schema as a CSV data in the same format as described in the
	 * metadata.csv
	 * 
	 * @return
	 */
	public static String[] getSchemaCSV() {
		int resultLen = 0;
		Iterator<Hashtable<String, Column>> sizeCountIt = schema.values()
				.iterator();
		while (sizeCountIt.hasNext()) {
			resultLen += sizeCountIt.next().size();
		}
		String[] result = new String[resultLen];
		int index = 0;
		Iterator<String> tableIt = schema.keySet().iterator();
		while (tableIt.hasNext()) {
			String tableName = tableIt.next();
			Hashtable<String, Column> currColumns = schema.get(tableName);
			Iterator<String> columnIt = currColumns.keySet().iterator();
			while (columnIt.hasNext()) {
				result[index++] = tableName + ", "
						+ currColumns.get(columnIt.next());
			}
		}
		return result;
	}

	public static void addNewTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) {

		Hashtable<String, Column> table = new Hashtable<String, Column>();

		Iterator<Entry<String, String>> colNameType = htblColNameType
				.entrySet().iterator();
		while (colNameType.hasNext()) {
			Entry<String, String> curr = colNameType.next();
			table.put(curr.getKey(), new Column(curr.getKey(), curr.getValue(),
					curr.getKey().equals(strKeyColName)));
		}

		Iterator<Entry<String, String>> colNameRef = htblColNameRefs.entrySet()
				.iterator();
		while (colNameRef.hasNext()) {
			Entry<String, String> curr = colNameRef.next();
			Column currCol = table.get(curr.getKey());
			currCol.setReference(curr.getValue());
		}

		schema.put(strTableName, table);

	}
}
