package com.nan.fokak;

import java.util.Hashtable;
import java.util.Iterator;

import org.junit.Test;

import com.nan.DBAppException;
import com.nan.DBEngineException;
import com.nan.schema.Record;
import com.nan.schema.Table;

public class Tests {
	public void testCreate() throws DBAppException {
		Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
		htblColNameType.put("Name", "String");
		htblColNameType.put("Age", "Integer");
		htblColNameType.put("Mail", "Integer");

		Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
		String strKeyColName = "Age";
		Table t = new Table("Person", htblColNameType, htblColNameRefs,
				strKeyColName);
		t.createIndex("Name");
	}

	public void testTable() throws DBAppException, DBEngineException {
		Table t = new Table("Person");
		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		htblColNameValue.put("Name", "Mohamed");
		t.deleteFromTable(htblColNameValue, "OR");
	}

	@Test
	public void testSelectFromTable() throws DBAppException, DBEngineException {
		Table t = new Table("Person");
		Hashtable<String, String> htblColNameValue = new Hashtable<String, String>();
		htblColNameValue.put("Name", "Mohamed");
		Iterator<Record> it = t.selectFromTable(htblColNameValue, "OR");
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
}
