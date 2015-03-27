package com.nan.commands;

import java.util.Hashtable;
import java.util.Set;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.schema.Schema;

public class InsertIntoTableCommand implements Command {
	private DBApp dataBase;
	private String tableName;
	private Hashtable<String, String> colNameValue;

	public InsertIntoTableCommand(DBApp dataBase, String tableName,
			Hashtable<String, String> colNameValue) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.colNameValue = colNameValue;
	}

	@Override
	public void execute() throws DBAppException {
		if (!Schema.checkTableExist(tableName)) {
			throw new DBAppException("Table " + tableName + " Doesn't Exists");
		} else {
			Set<String> colNamesSet = colNameValue.keySet();
			for (String colName : colNamesSet) {
				throw new DBAppException("Column " + colName
						+ " Doesn't Exists");
			}
		}
		dataBase.insertIntoTable(tableName, colNameValue);
		System.out.println("One row effected!");
	}

}
