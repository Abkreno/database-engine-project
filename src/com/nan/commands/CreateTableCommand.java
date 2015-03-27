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
		if (Schema.checkTableExist(tableName)) {
			System.err.println("Table " + tableName + " Already Exists");
			throw new DBAppException();
		}
		dataBase.createTable(tableName, htblColNameType, htblColNameRefs,
				strKeyColName);
		System.out.println("Table '" + tableName + "' created successfully!");
	}

}
