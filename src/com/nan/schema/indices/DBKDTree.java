package com.nan.schema.indices;

import libs.data_structures.kDTree.KDTree;

public class DBKDTree extends KDTree {

	public DBKDTree(int k) {
		super(k);
	}

	public void insert(Object[] key, Object value) {
		Integer[] modifiedKeys = modifiyKeys(key);

		super.insert(modifiedKeys, value);
	}

	public void delete(Object[] key) {
		Integer[] modifiedKeys = modifiyKeys(key);
		super.delete(modifiedKeys);
	}

	private Integer[] modifiyKeys(Object[] key) {
		Integer[] modifiedKeys = new Integer[key.length];
		for (int i = 0; i < key.length; i++) {
			if (key[i] instanceof String) {
				modifiedKeys[i] = Integer.parseInt(key[i].toString());
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

}
