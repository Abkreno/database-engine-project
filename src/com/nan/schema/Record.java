package com.nan.schema;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class Record implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5540495794645302911L;
	private Hashtable<String, String> colNameValue;
	private boolean deleted;

	public Record(Hashtable<String, String> htblColNameValue) {
		this.colNameValue = htblColNameValue;
	}

	public boolean containsColumnValue(String colName, String value) {
		return colNameValue.get(colName).equals(value);
	}

	public boolean containsAllColumnValues(
			Hashtable<String, String> htblColNameValue) {
		Iterator<Entry<String, String>> entries = htblColNameValue.entrySet()
				.iterator();
		while (entries.hasNext()) {
			Entry<String, String> entry = entries.next();
			if (!containsColumnValue(entry.getKey(), entry.getValue())) {
				return false;
			}
		}
		return true;
	}

	public boolean containsAnyColumnValues(
			Hashtable<String, String> htblColNameValue) {
		Iterator<Entry<String, String>> entries = htblColNameValue.entrySet()
				.iterator();
		while (entries.hasNext()) {
			Entry<String, String> entry = entries.next();
			if (containsColumnValue(entry.getKey(), entry.getValue())) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return colNameValue.toString();
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

}
