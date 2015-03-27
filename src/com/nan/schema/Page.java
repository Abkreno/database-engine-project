package com.nan.schema;

import java.util.Hashtable;

import com.nan.DBEngineException;
import com.nan.utils.FileManager;
import com.nan.utils.PropertiesReader;

public class Page {
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
			save();
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
		save();
	}

	/**
	 * set the record with the given id to null
	 * 
	 * @param id
	 */
	public void deleteFromPage(String id) {
		deleteFromPage(searchForRowNumber(id));
	}

	/**
	 * linear search for the row of a record , returns -1 if not found
	 * 
	 * @param id
	 */
	public int searchForRowNumber(String id) {
		// TODO linear search for the row of a record with recordId = id
		for (int i = 0; i < rowCount; i++) {
			
		}
		return -1;
	}

	public void save() {
		FileManager.savePage(tableName, pageNumber, pageRows);
	}
}
