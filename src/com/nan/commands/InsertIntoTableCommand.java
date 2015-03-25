package com.nan.commands;

import java.util.Hashtable;

import com.nan.DBApp;
import com.nan.DBAppException;

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
		dataBase.insertIntoTable(tableName, colNameValue);
		System.out.println("One row effected!");
	}

}
