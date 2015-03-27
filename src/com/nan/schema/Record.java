package com.nan.schema;

import java.io.Serializable;
import java.util.Hashtable;

@SuppressWarnings("serial")
public class Record implements Serializable {
	private Hashtable<String, String> colNameValue;

	public Record(Hashtable<String, String> htblColNameValue) {
		this.colNameValue = htblColNameValue;
	}

}
