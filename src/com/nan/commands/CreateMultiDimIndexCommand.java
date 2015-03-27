package com.nan.commands;

import java.util.Hashtable;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.schema.Schema;

public class CreateMultiDimIndexCommand implements Command {
	private DBApp dataBase;
	private String tableName, colName1, colName2;
	private Hashtable<String, String> colNames;

	public CreateMultiDimIndexCommand(DBApp dataBase, String tableName,
			Hashtable<String, String> colNames) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.colNames = colNames;
	}

	@Override
	public void execute() throws DBAppException {
		if (!Schema.checkTableExist(tableName)) {
			System.err.println("Table " + tableName + " Doesn't Exists");
			throw new DBAppException();
		} else if (!Schema.checkColExist(tableName, colName1)) {
			System.err.println("Column " + colName1 + " Doesn't Exists");
			throw new DBAppException();
		} else if (!Schema.checkColExist(tableName, colName2)) {
			System.err.println("Column " + colName2 + " Doesn't Exists");
			throw new DBAppException();
		} else if (Schema.checkMultiIndexExist(tableName, colName1, colName2)) {
			System.err.println("MultiIndex On " + colName1 + " , " + colName2
					+ " Already Exists");
			throw new DBAppException();
		}
		dataBase.createMultiDimIndex(tableName, colNames);
		System.out.println("MultiIndex created on table '" + tableName + "'");
	}

}
