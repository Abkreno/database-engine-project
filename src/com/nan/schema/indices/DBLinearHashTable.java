package com.nan.schema.indices;

import java.io.Serializable;

import libs.data_structures.linearHashTable.LinearHashTable;

import com.nan.utils.PropertiesReader;

public class DBLinearHashTable extends LinearHashTable implements Serializable {
	private boolean isInit;

	public DBLinearHashTable(boolean unInizialized) {
		super(unInizialized);
		isInit = false;
	}

	public DBLinearHashTable() {
		super(PropertiesReader.LHT_LOAD_FACTOR,
				PropertiesReader.LHT_BUCKET_SIZE);
		isInit = true;
	}

	public boolean isIniziliazed() {
		return isInit;
	}

	public void insert(Object key, String value) {
		super.put(modifyKey(key), value);
	}

	private Integer modifyKey(Object key) {
		Integer modifiedKey = null;
		if (key instanceof String) {
			modifiedKey = decodeString(key.toString());
		} else if (key instanceof Integer) {
			modifiedKey = ((Integer) key).intValue();
		} else if (key instanceof Character) {
			modifiedKey = Character.getNumericValue((Character) key);
		} else if (key instanceof Boolean) {
			modifiedKey = ((Boolean) key) == Boolean.TRUE ? 1 : 0;
		}
		return modifiedKey;
	}

	public Object delete(Object key) {
		return super.remove(modifyKey(key));
	}

	public Integer decodeString(String str) {
		int hash = 7;
		int mod = 100000007;
		for (int i = 0; i < str.length(); i++) {
			hash = (((hash * 31) % mod) + str.charAt(i)) % mod;
		}
		return hash;
	}
}
