package com.nan.schema;

public class Column {
	private String colName, colType;
	private boolean key, indexed;

	public Column(String colName, String colType, boolean key, boolean indexed) {
		this.colName = colName;
		this.colType = colType;
		this.key = key;
		this.indexed = indexed;
	}
}
