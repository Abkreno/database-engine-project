package com.nan.commands;

import com.nan.DBApp;
import com.nan.DBAppException;

public class CreateIndexCommand implements Command {
	private DBApp dataBase;
	private String tableName, colName;

	public CreateIndexCommand(DBApp dataBase, String tableName, String colName) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.colName = colName;
	}

	@Override
	public void execute() throws DBAppException {
		dataBase.createIndex(tableName, colName);
		System.out.println("Index created on table '" + tableName + "'");
	}

}
