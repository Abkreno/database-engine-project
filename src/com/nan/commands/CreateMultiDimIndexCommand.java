package com.nan.commands;

import java.util.Hashtable;

import com.nan.DBApp;
import com.nan.DBAppException;

public class CreateMultiDimIndexCommand implements Command {
	private DBApp dataBase;
	private String tableName;
	private Hashtable<String, String> colNames;

	public CreateMultiDimIndexCommand(DBApp dataBase, String tableName,
			Hashtable<String, String> colNames) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.colNames = colNames;
	}

	@Override
	public void execute() throws DBAppException {
		dataBase.createMultiDimIndex(tableName, colNames);
		System.out.println("MultiIndex created on table '" + tableName + "'");
	}

}
