package com.nan.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import com.nan.DBAppException;
import com.nan.schema.indices.DBKDTree;
import com.nan.schema.indices.DBLinearHashTable;
import com.nan.utils.FileManager;
import com.nan.utils.ObjectManager;

public class Table {
	private int tablePageCount;
	private String tableName;
	private Page currentPage;
	private Hashtable<Integer, Page> tablePages;
	private Hashtable<String, DBLinearHashTable> tableIndices;
	private Hashtable<String, DBKDTree> tableMultiIndices;

	/**
	 * Creates a Table and inizializes it's files (This constructor will only be
	 * called once a table is created for the first time)
	 * 
	 * @param strTableName
	 *            Table name (the folder of the table will be named as this
	 *            value)
	 * @param htblColNameType
	 *            Table Columns which will be stored in the metadata.csv file
	 * @param htblColNameRefs
	 *            Columns Refrences will be stored in the metadata.csv file
	 * @param strKeyColName
	 *            An Index will be generated automatically on this key and
	 *            stored on the indices file for this table
	 * @throws DBAppException
	 */
	public Table(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBAppException {

		this.tablePageCount = 0;
		FileManager.createNewTable(strTableName);
		Schema.addNewTable(strTableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		createIndex(strKeyColName);
	}

	/**
	 * This constructor is called to create a table that already exists in the
	 * data file
	 * 
	 * @param strTableName
	 */
	public Table(String strTableName) {
		tablePageCount = FileManager.getPageFilesCount(strTableName);
		try {
			ArrayList<String> indciesNames = FileManager
					.readTableIndicies(tableName);
			for (String index : indciesNames) {
				if (index.contains("*")) {
					tableMultiIndices.put(getMultiIndexId(index.split("*")),
							null);
				} else {
					tableIndices.put(index, null);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentPage = ObjectManager.readPage(tableName, tablePageCount);
		tablePages.put(tablePageCount, currentPage);
	}

	public void createIndex(String colName) throws DBAppException {
		if (tableIndices.containsKey(colName)) {
			throw new DBAppException("Index on Column " + colName
					+ " Already Exists");
		}
		DBLinearHashTable index = new DBLinearHashTable();
		tableIndices.put(colName, index);
		ObjectManager.writeIndex(tableName, colName, index);
	}

	public void createMultiIndex(Hashtable<String, String> htblColNames) {
		HashSet<String> colNames = new HashSet<String>();
		Iterator<Entry<String, String>> entries = htblColNames.entrySet()
				.iterator();
		while (entries.hasNext()) {
			Entry<String, String> entry = entries.next();
			colNames.add(entry.getValue());
			colNames.add(entry.getKey());
		}
		Object[] colNamesArr = colNames.toArray();
		String colNamesId = getMultiIndexId(colNamesArr);

		DBKDTree multiIndex = new DBKDTree(colNamesArr.length);
		tableMultiIndices.put(colNamesId, multiIndex);
	}

	public String getMultiIndexId(Object[] colNamesArr) {
		String colNamesId = "";
		Arrays.sort(colNamesArr);
		for (int i = 0; i < colNamesArr.length; i++) {
			colNamesId += colNamesArr[i]
					+ (i == colNamesArr.length - 1 ? "" : "*");
		}
		return colNamesId;
	}
}
