package com.nan.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.nan.schema.indices.DBKDTree;
import com.nan.schema.indices.DBLinearHashTable;
import com.nan.utils.FileManager;

public class Table {
	private String tableName;
	private Hashtable<Integer, Page> tablePages;
	private int tablePageCount;
	private Hashtable<String, DBLinearHashTable> tableIndices;
	private Hashtable<String[], DBKDTree> tableMultiIndices;

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
	 */
	public Table(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) {

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
		this.tablePageCount = FileManager.getPageFilesCount();
		try {
			ArrayList<String> indciesNames = FileManager
					.readTableIndicies(tableName);
			for (String index : indciesNames) {
				if (index.contains("*")) {
					tableMultiIndices.put(index.split("*"), null);
				} else {
					tableIndices.put(index, null);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createIndex(String strKeyColName) {
		// TODO Auto-generated method stub
	}

}
