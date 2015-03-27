package com.nan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Hashtable;

import libs.data_structures.kDTree.KDTree;

import org.junit.Test;

import com.nan.commands.Command;
import com.nan.commands.CreateTableCommand;
import com.nan.schema.Table;
import com.nan.schema.indices.DBKDTree;
import com.nan.schema.indices.DBLinearHashTable;
import com.nan.utils.PropertiesReader;

public class DBAppTest {
	@Test
	public void testPropertiesReader() throws IOException {
		int maximumRowsCountinPage = Integer.parseInt(PropertiesReader
				.readProperty("MaximumRowsCountinPage"));
		int bPlusTreeN = Integer.parseInt(PropertiesReader
				.readProperty("BPlusTreeN"));
		double LHTLoadFactor = Double.parseDouble(PropertiesReader
				.readProperty("LHTLoadFactor"));
		assertTrue("Maximum Rows Count in Page should be 200",
				maximumRowsCountinPage == 200);
		assertTrue("BPlusTree N should be 20", bPlusTreeN == 20);
		assertTrue("LinearHashTable LoadFactor should be .75f",
				LHTLoadFactor == 0.75f);
	}

	@Test
	public void testLinearHashTableInsert() {
		DBLinearHashTable table = new DBLinearHashTable();
		table.insert(1, "Abkr");
		table.insert(2, "Kady");
		table.insert(3, "Abdo");
		String abkr = table.get(1);
		assertEquals(abkr, "Abkr");
		String kady = table.get(2);
		assertEquals(kady, "Kady");
		String abdo = table.get(3);
		assertEquals(abdo, "Abdo");
	}

	@Test
	public void testLinearHashTableDelete() {
		DBLinearHashTable table = new DBLinearHashTable();
		table.insert(1, "Abkr");
		table.insert(2, "Kady");
		table.insert(3, "Abdo");
		table.delete(1);
		String abkr = table.get(1);
		assertEquals("The value of abkr should be null", abkr, null);
	}

	@Test
	public void testKDTreeInsert() {
		DBKDTree tree = new DBKDTree(2);
		Object[] keys1 = { "Abkr", 9771 };
		tree.insert(keys1, "Abkr, 28-9771");
		Object[] keys2 = { "Kady", 9799 };
		tree.insert(keys2, "Kady, 28-9799");
		Object[] keys3 = { "Abdo", 10500 };
		tree.insert(keys3, "Abdo, 28-10500");
		String abkr = tree.search(keys1).toString();
		assertEquals(abkr, "Abkr, 28-9771");
		String kady = tree.search(keys2).toString();
		assertEquals(kady, "Kady, 28-9799");
		String abdo = tree.search(keys3).toString();
		assertEquals(abdo, "Abdo, 28-10500");
	}

	@Test
	public void testCreate() throws DBAppException {
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

	@Test
	public void testInsert() throws DBAppException {
		Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
		htblColNameType.put("Name", "String");
		htblColNameType.put("Age", "Integer");
		htblColNameType.put("Mail", "Integer");

		Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
		String strKeyColName = "Age";
		Table t = new Table("Person", htblColNameType, htblColNameRefs,
				strKeyColName);
		Hashtable<String, String> htb = new Hashtable<String, String>();
		htb.put("Name", "hey");
		htb.put("Age", "11");
		htb.put("Mail", "1@hotmail.com");
		t.insertIntoTable(htb);
	}

	@Test
	public void testDelete() throws DBAppException {
		Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
		htblColNameType.put("Name", "String");
		htblColNameType.put("Age", "Integer");
		htblColNameType.put("Mail", "Integer");

		Hashtable<String, String> htblColNameRefs = new Hashtable<String, String>();
		String strKeyColName = "Age";
		Table t = new Table("Person", htblColNameType, htblColNameRefs,
				strKeyColName);
		Hashtable<String, String> htb = new Hashtable<String, String>();
		htb.put("Name", "hey");
		htb.put("Age", "11");
		htb.put("Mail", "1@hotmail.com");
		t.insertIntoTable(htb);
		try {
			t.deleteFromTable(htb, "And");
		} catch (DBEngineException e) {
			e.printStackTrace();
		}
	}

}
