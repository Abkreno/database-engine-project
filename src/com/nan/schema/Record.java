package com.nan.schema;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Record implements Serializable {
	private ArrayList<String> values;

	public Record(ArrayList<String> values) {
		this.values = values;
	}

}
