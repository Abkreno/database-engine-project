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

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

}
