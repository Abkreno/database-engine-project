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
			System.err.println("Table " + tableName + " Doesn't Exists");
			throw new DBAppException();
		} else {
			Set<String> colNamesSet = colNameValue.keySet();
			for (String colName : colNamesSet) {
				System.err.println("Column " + colName + " Doesn't Exists");
				throw new DBAppException();
			}
		}
		dataBase.insertIntoTable(tableName, colNameValue);
		System.out.println("One row effected!");
	}

}
