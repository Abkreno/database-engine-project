package com.nan.schema;

import java.io.Serializable;
import java.util.Hashtable;

import com.nan.DBEngineException;
import com.nan.utils.FileManager;
import com.nan.utils.PropertiesReader;

public class Page implements Serializable {
	private String tableName;
	private int pageNumber, rowCount;
	private Hashtable<Integer, Record> pageRows;

	public Page(String tableName, int pageNumber) {
		this.tableName = tableName;
		this.pageNumber = pageNumber;
		this.rowCount = 0;
		this.pageRows = new Hashtable<Integer, Record>();
		save();
	}

	public boolean isFull() {
		return pageRows.size() == PropertiesReader.MAX_ROW_COUNT_IN_PAGE;
	}

	/**
	 * inserts a record to the current row count, throws an exception if the
	 * page is full
	 * 
	 * @param value
	 * @throws DBEngineException
	 */
	public void insertInPage(Record values) throws DBEngineException {
		if (!isFull()) {
			pageRows.put(rowCount++, values);
		} else {
			throw new DBEngineException("Table " + tableName + "'s Page ("
					+ pageNumber + ")is Full!");
		}
	}

	/**
	 * set the record at row (rowNumber) to null
	 * 
	 * @param rowNumber
	 */
	public void deleteFromPage(int rowNumber) {
		pageRows.put(rowNumber, null);
	}

	/**
	 * set the record with the given id to null
	 * 
	 * @param id
	 */
	public void deleteFromPage(Hashtable<String, String> htblColNameValue,
			String operator) {
		boolean and = operator.equalsIgnoreCase("AND");
		for (int i = 0; i < rowCount; i++) {
			Record currRecord = pageRows.get(i);

			if (and && currRecord.containsAllColumnValues(htblColNameValue)) {
				deleteFromPage(i);
			} else if (currRecord.containsAnyColumnValues(htblColNameValue)) {
				deleteFromPage(i);
			}
		}

	}

	public void save() {
		FileManager.savePage(tableName, pageNumber, pageRows);
	}
}
