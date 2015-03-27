package com.nan.commands;

import java.util.Hashtable;
import java.util.Set;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.schema.Schema;

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
		if (!Schema.checkTableExist(tableName)) {
			System.err.println("Table " + tableName + " Doesn't Exists");
			throw new DBAppException();
		} else {
			Set<String> colNamesSet = colNames.keySet();
			for (String colName : colNamesSet) {
				System.err.println("Column " + colName + " Doesn't Exists");
				throw new DBAppException();
			}
		}
		if (Schema.checkMultiIndexExist(tableName, colNames)) {
			System.err.println("MultiIndex On These Columns Already Exists");
			throw new DBAppException();
		}
		dataBase.createMultiDimIndex(tableName, colNames);
		System.out.println("MultiIndex created on table '" + tableName + "'");
	}

}
