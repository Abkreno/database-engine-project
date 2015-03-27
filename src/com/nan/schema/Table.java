package com.nan.schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

import com.nan.DBAppException;
import com.nan.DBEngineException;
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

	private static Hashtable<String, Column> tableSchema;

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
		this.tableSchema = Schema.getTableSchema(tableName);
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
		this.tableSchema = Schema.getTableSchema(tableName);
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

	public void createMultiIndex(Hashtable<String, String> htblColNames)
			throws DBAppException {
		HashSet<String> colNames = new HashSet<String>();
		Iterator<Entry<String, String>> entries = htblColNames.entrySet()
				.iterator();
		while (entries.hasNext()) {
			Entry<String, String> entry = entries.next();
			colNames.add(entry.getValue());
			colNames.add(entry.getKey());
		}
		Object[] colNamesArr = colNames.toArray();
		Arrays.sort(colNamesArr);
		String colNamesId = getMultiIndexId(colNamesArr);
		if (tableMultiIndices.containsKey(colNamesId)) {
			throw new DBAppException("Multi Index on Columns "
					+ Arrays.toString(colNamesArr) + " Already Exists");
		}
		DBKDTree multiIndex = new DBKDTree(colNamesArr.length);
		tableMultiIndices.put(colNamesId, multiIndex);
	}

	public String getMultiIndexId(Object[] colNamesArr) {
		String colNamesId = "";
		for (int i = 0; i < colNamesArr.length; i++) {
			colNamesId += colNamesArr[i]
					+ (i == colNamesArr.length - 1 ? "" : "*");
		}
		return colNamesId;
	}

	public void insertIntoTable(Hashtable<String, String> htblColNameValue)
			throws DBAppException {
		if (currentPage.isFull()) {
			tablePageCount++;
			currentPage = new Page(tableName, tablePageCount);
			ObjectManager.writePage(tableName, tablePageCount, currentPage);
		}
		try {
			currentPage.insertInPage(new Record(htblColNameValue));
			currentPage.save();
		} catch (DBEngineException e) {
			e.printStackTrace();
		}
		insetIntoIndecies(htblColNameValue);
	}

	private void insetIntoIndecies(Hashtable<String, String> htblColNameValue) {
		Iterator<String> colNames = htblColNameValue.keySet().iterator();
		Object[] colNamesArr = new Object[htblColNameValue.size()];
		int index = 0;
		while (colNames.hasNext()) {
			String colName = colNames.next();
			if (tableIndices.contains(colName)) {
				checkIndexInizialized(colName);
				tableIndices.get(colName).insert(htblColNameValue.get(colName),
						tablePageCount + "," + (currentPage.getRowCount() - 1));
			}
			colNamesArr[index++] = colName;
		}
		Arrays.sort(colNamesArr);
		String colNamesId = getMultiIndexId(colNamesArr);
		if (tableMultiIndices.containsKey(colNamesId)) {
			checkMultiIndexInizialized(colNamesId);
			Object[] keyValues = new Object[colNamesArr.length];
			for (int i = 0; i < keyValues.length; i++) {
				keyValues[i] = htblColNameValue.get(colNamesArr[i]);
			}
			tableMultiIndices.get(colNamesId).insert(keyValues,
					tablePageCount + "," + (currentPage.getRowCount() - 1));
		}
	}

	private boolean deleteFromIndecies(
			Hashtable<String, String> htblColNameValue, boolean and) {
		Iterator<String> colNames = htblColNameValue.keySet().iterator();
		Object[] colNamesArr = new Object[htblColNameValue.size()];
		int index = 0;
		while (colNames.hasNext()) {
			String colName = colNames.next();
			if (tableIndices.contains(colName)) {
				checkIndexInizialized(colName);
				Object result = tableIndices.get(colName).delete(
						htblColNameValue.get(colName));
				String[] data = result.toString().split(",");
				int pageNumber = Integer.parseInt(data[0]);
				int rowNumber = Integer.parseInt(data[1]);
				Page page = null;
				if (!tablePages.contains(pageNumber)) {
					page = ObjectManager.readPage(tableName, pageNumber);
					tablePages.put(pageNumber, page);
				} else {
					page = tablePages.get(pageNumber);
				}
				page.deleteFromPage(rowNumber);
				page.save();

				return true;
			}
			colNamesArr[index++] = colName;
		}
		Arrays.sort(colNamesArr);
		String colNamesId = getMultiIndexId(colNamesArr);
		if (tableMultiIndices.containsKey(colNamesId)) {
			checkMultiIndexInizialized(colNamesId);
			Object[] keyValues = new Object[colNamesArr.length];
			for (int i = 0; i < keyValues.length; i++) {
				keyValues[i] = htblColNameValue.get(colNamesArr[i]);
			}
			Object result = tableMultiIndices.get(colNamesId).delete(keyValues);
			String[] data = result.toString().split(",");
			int pageNumber = Integer.parseInt(data[0]);
			int rowNumber = Integer.parseInt(data[1]);
			Page page = null;
			if (!tablePages.contains(pageNumber)) {
				page = ObjectManager.readPage(tableName, pageNumber);
				tablePages.put(pageNumber, page);
			} else {
				page = tablePages.get(pageNumber);
			}
			page.deleteFromPage(rowNumber);
			page.save();
			return true;
		}
		return false;
	}

	private void checkIndexInizialized(String colName) {
		if (!tableIndices.get(colName).isIniziliazed()) {
			tableIndices.put(colName,
					ObjectManager.readIInex(tableName, colName));
		}
	}

	private void checkMultiIndexInizialized(String colNamesId) {
		if (!tableMultiIndices.get(colNamesId).isIniziliazed()) {
			tableMultiIndices.put(colNamesId,
					ObjectManager.readMultiInex(tableName, colNamesId));
		}
	}

	public void deleteFromTable(Hashtable<String, String> htblColNameValue,
			String strOperator) throws DBEngineException {
		if (deleteFromIndecies(htblColNameValue,
				strOperator.equalsIgnoreCase("AND"))) {
			return;
		}
		// In case there are no indicies
		for (int i = 0; i < tablePageCount; i++) {
			Page page;
			if (!tablePages.contains(i)) {
				page = ObjectManager.readPage(tableName, i);
				tablePages.put(i, page);
			} else {
				page = tablePages.get(i);
			}
			page.deleteFromPage(htblColNameValue, strOperator);
			page.save();

		}
	}

	public void saveAllPages() {
		Iterator<Page> it = tablePages.values().iterator();
		while (it.hasNext()) {
			it.next().save();
		}
	}
}
