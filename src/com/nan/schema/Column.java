package com.nan.schema;

public class Column {
	private String colName, colType, reference;
	private boolean key, indexed;

	public Column(String colName, String colType, boolean key, boolean indexed,
			String reference) {
		this.colName = colName;
		this.colType = colType;
		this.key = key;
		this.indexed = indexed;
		this.reference = reference;
	}

	public String toString() {
		return this.colName + ", " + this.colType + ", " + this.key + ", "
				+ this.indexed + ", " + this.reference;
	}
}
