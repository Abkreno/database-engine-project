package com.nan.schema.indices;

import com.nan.utils.PropertiesReader;

import libs.data_structures.linearHashTable.LinearHashTable;

public class DBLinearHashTable extends LinearHashTable {

	public DBLinearHashTable() {
		super(
				Integer.parseInt(PropertiesReader.readProperty("LHTLoadFactor")),
				Integer.parseInt(PropertiesReader.readProperty("LHTBucketSize")));
	}

	public void insert(Integer key, String value) {
		super.put(key, value);
	}

	public void delete(Integer key) {
		super.remove(key);
	}
}
