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
		this.tablePages = new Hashtable<Integer, Page>();
		this.tableIndices = new Hashtable<String, DBLinearHashTable>();
		this.tableMultiIndices = new Hashtable<String, DBKDTree>();
		this.tablePageCount = 0;
		this.tableName = strTableName;
		FileManager.createNewTable(strTableName);
		Schema.addNewTable(strTableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		createIndex(strKeyColName);
		currentPage = new Page(tableName, tablePageCount);
		ObjectManager.writePage(tableName, tablePageCount, currentPage);
	}

	/**
	 * This constructor is called to create a table that already exists in the
	 * data file
	 * 
	 * @param strTableName
	 */
	public Table(String strTableName) {
		this.tableName = strTableName;
		this.tablePageCount = FileManager.getPageFilesCount(strTableName) - 1;
		this.tablePages = new Hashtable<Integer, Page>();
		this.tableIndices = new Hashtable<String, DBLinearHashTable>();
		this.tableMultiIndices = new Hashtable<String, DBKDTree>();
		try {
			ArrayList<String> indciesNames = FileManager
					.readTableIndicies(tableName);
			for (String index : indciesNames) {
				if (index.contains("*")) {
					tableMultiIndices.put(getMultiIndexId(index.split("*")),
							new DBKDTree(false));
				} else {
					tableIndices.put(index, new DBLinearHashTable(false));
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
		try {
			FileManager.writeTableIndicies(tableName, colName);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public void drop() {
		String userDirectory = System.getProperty("user.dir");
		String tablesDirectory = userDirectory + "/data/tables/";
		String folderPath = tablesDirectory + this.tableName;

		FileManager.deleteFolder(folderPath);

		// TODO Delete from Schema when
	}
}
