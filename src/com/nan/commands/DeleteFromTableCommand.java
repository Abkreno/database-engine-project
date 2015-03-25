package com.nan.commands;

import java.util.Hashtable;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.DBEngineException;

public class DeleteFromTableCommand implements Command {
	private DBApp dataBase;
	private String tableName, operator;
	private Hashtable<String, String> colNameValue;

	public DeleteFromTableCommand(DBApp dataBase, String tableName,
			Hashtable<String, String> colNameValue, String operator) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.colNameValue = colNameValue;
		this.operator = operator;
	}

	@Override
	public void execute() throws DBAppException, DBEngineException {
		dataBase.deleteFromTable(tableName, colNameValue, operator);
		System.out.println("One row deleted!");
	}

}
