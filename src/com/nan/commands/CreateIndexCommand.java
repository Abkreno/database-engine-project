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
			System.err.println("Table " + tableName + " Doesn't Exists");
			throw new DBAppException();
		} else if (!Schema.checkColExist(tableName, colName)) {
			System.err.println("Column " + colName + " Doesn't Exists");
			throw new DBAppException();
		} else if (Schema.checkIndexExist(tableName, colName)) {
			System.err.println("Index On " + colName + " Already Exists");
			throw new DBAppException();
		}
		dataBase.createIndex(tableName, colName);
		System.out.println("Index created on table '" + tableName + "'");
	}
}
