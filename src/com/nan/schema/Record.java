package com.nan.schema;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Record implements Serializable, Comparable<String> {
	private ArrayList<String> values;

	public Record(ArrayList<String> values) {
		this.values = values;
	}

	@Override
	public int compareTo(String o) {
		return values.get(0).compareTo(o);
	}

}
