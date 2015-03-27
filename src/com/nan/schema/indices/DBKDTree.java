package com.nan.schema.indices;

import java.io.Serializable;

import libs.data_structures.kDTree.KDTree;

public class DBKDTree extends KDTree implements Serializable {
	private boolean isInit;

	public DBKDTree(int k) {
		super(k);
		isInit = true;
	}

	public DBKDTree(boolean unInizialized) {
		super(unInizialized);
		isInit = false;
	}

	public boolean isIniziliazed() {
		return isInit;
	}

	public void insert(Object[] key, Object value) {
		Integer[] modifiedKeys = modifiyKeys(key);
		super.insert(modifiedKeys, value);
	}

	public void delete(Object[] key) {
		Integer[] modifiedKeys = modifiyKeys(key);
		super.delete(modifiedKeys);
	}

	public Object search(Object[] key) {
		Integer[] modifiedKeys = modifiyKeys(key);
		return super.search(modifiedKeys);
	}

	private Integer[] modifiyKeys(Object[] key) {
		Integer[] modifiedKeys = new Integer[key.length];
		for (int i = 0; i < key.length; i++) {
			if (key[i] instanceof String) {
				modifiedKeys[i] = decodeString(key[i].toString());
			} else if (key[i] instanceof Integer) {
				modifiedKeys[i] = ((Integer) key[i]).intValue();
			} else if (key[i] instanceof Character) {
				modifiedKeys[i] = Character.getNumericValue((Character) key[i]);
			} else if (key[i] instanceof Boolean) {
				modifiedKeys[i] = ((Boolean) key[i]) == Boolean.TRUE ? 1 : 0;
			}
		}
		return modifiedKeys;
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
