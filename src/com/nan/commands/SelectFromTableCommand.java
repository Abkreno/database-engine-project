package com.nan.commands;

import java.util.Hashtable;
import java.util.Iterator;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.DBEngineException;

public class SelectFromTableCommand implements Command {
	private DBApp dataBase;
	private String tableName, operator;
	private Hashtable<String, String> colNameValue;

	public SelectFromTableCommand(DBApp dataBase, String tableName,
			Hashtable<String, String> colNameValue, String operator) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.colNameValue = colNameValue;
		this.operator = operator;
	}

	@Override
	public void execute() throws DBAppException, DBEngineException {

	}

	public Iterator executeAndReturn() throws DBEngineException {
		// TODO Print Selected Rows
		return dataBase.selectFromTable(tableName, colNameValue, operator);
	}
}
