package com.nan.schema.indices;

import com.nan.utils.PropertiesReader;

import libs.data_structures.linearHashTable.LinearHashTable;

public class DBLinearHashTable extends LinearHashTable {

	public DBLinearHashTable() {
		super(PropertiesReader.LHT_LOAD_FACTOR,
				PropertiesReader.LHT_BUCKET_SIZE);
	}

	public void insert(Integer key, String value) {
		super.put(key, value);
	}

	public void delete(Integer key) {
		super.remove(key);
	}
}
