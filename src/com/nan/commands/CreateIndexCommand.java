package com.nan.commands;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.schema.Schema;

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
		if (!Schema.checkTableExist(tableName)) {
			throw new DBAppException("Table " + tableName + " Doesn't Exists");
		} else if (!Schema.checkColExist(tableName, colName)) {
			throw new DBAppException("Column " + colName + " Doesn't Exists");
		} else if (Schema.checkIndexExist(tableName, colName)) {
			throw new DBAppException("Index On " + colName + " Already Exists");
		}
		dataBase.createIndex(tableName, colName);
		System.out.println("Index created on table '" + tableName + "'");
	}
}
