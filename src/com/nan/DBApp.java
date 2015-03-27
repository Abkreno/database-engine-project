package com.nan;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;

import com.nan.schema.Page;
import com.nan.schema.Table;
import com.nan.utils.FileManager;

public class DBApp {
	public Hashtable<String, Table> cachedTables;

	public void init() {

	}

	public void createTable(String strTableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName)
			throws DBAppException {
		Table table = new Table(strTableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		cachedTables.put(strTableName, table);
	}

	public void createIndex(String strTableName, String strColName)
			throws DBAppException {
		if (!cachedTables.contains(strTableName)) {
			cachedTables.put(strTableName, new Table(strTableName));
		}
		Table table = cachedTables.get(strTableName);
	}

	public void createMultiDimIndex(String strTableName,
			Hashtable<String, String> htblColNames) throws DBAppException {
		if (!cachedTables.contains(strTableName)) {
			cachedTables.put(strTableName, new Table(strTableName));
		}
		Table table = cachedTables.get(strTableName);
	}

	public void insertIntoTable(String strTableName,
			Hashtable<String, String> htblColNameValue) throws DBAppException {
		if (!cachedTables.contains(strTableName)) {
			cachedTables.put(strTableName, new Table(strTableName));
		}
		Table table = cachedTables.get(strTableName);
	}

	public void deleteFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		if (!cachedTables.contains(strTableName)) {
			cachedTables.put(strTableName, new Table(strTableName));
		}
		Table table = cachedTables.get(strTableName);
		saveAll();
	}

	public Iterator selectFromTable(String strTableName,
			Hashtable<String, String> htblColNameValue, String strOperator)
			throws DBEngineException {
		if (!cachedTables.contains(strTableName)) {
			cachedTables.put(strTableName, new Table(strTableName));
		}
		Table table = cachedTables.get(strTableName);
		saveAll();
		return null;
	}

	public void saveAll() throws DBEngineException {
		try {
			FileManager.writeSchema();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Table> it = cachedTables.values().iterator();
		while (it.hasNext()) {
			it.next().saveAllPages();
		}
	}
}
