package com.nan.commands;

import java.util.Hashtable;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.schema.Schema;

public class CreateTableCommand implements Command {
	private DBApp dataBase;
	private String tableName, strKeyColName;
	private Hashtable<String, String> htblColNameType, htblColNameRefs;

	public CreateTableCommand(DBApp dataBase, String tableName,
			Hashtable<String, String> htblColNameType,
			Hashtable<String, String> htblColNameRefs, String strKeyColName) {
		this.dataBase = dataBase;
		this.tableName = tableName;
		this.htblColNameType = htblColNameType;
		this.htblColNameRefs = htblColNameRefs;
		this.strKeyColName = strKeyColName;
	}

	@Override
	public void execute() throws DBAppException {
		if (tableName.contains("*")) {
			throw new DBAppException("Table names can't contain * ");
		} else if (Schema.checkTableExist(tableName)) {
			throw new DBAppException("Table " + tableName + " Already Exists");
		}
		dataBase.createTable(tableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		System.out.println("Table '" + tableName + "' created successfully!");
	}

}
