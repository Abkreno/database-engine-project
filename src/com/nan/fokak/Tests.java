package com.nan.fokak;

import java.util.Hashtable;

import org.junit.Test;

import com.nan.DBAppException;
import com.nan.schema.Table;

public class Tests {

	@Test
	public void testTable() throws DBAppException {

		Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
		htblColNameType.put("Name", "String");
		htblColNameType.put("Age", "Integer");
		htblColNameType.put("Mail", "Integer");

		Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
		String strKeyColName = "Age";
		Table t = new Table("Person", htblColNameType, htblColNameRefs,
				strKeyColName);

		Table t2 = new Table("Person");
	}
}
