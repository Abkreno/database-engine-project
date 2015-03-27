package com.nan.commands;

import java.util.Hashtable;
import java.util.Set;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.DBEngineException;
import com.nan.schema.Schema;

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
		if (!Schema.checkTableExist(tableName)) {
			throw new DBAppException("Table " + tableName + " Doesn't Exists");
		}
		dataBase.deleteFromTable(tableName, colNameValue, operator);
		System.out.println("One row deleted!");
	}

}
