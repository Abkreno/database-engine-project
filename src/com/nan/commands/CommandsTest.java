package com.nan.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Hashtable;

import org.junit.Test;

import com.nan.DBApp;
import com.nan.DBAppException;
import com.nan.DBEngineException;
import com.nan.schema.Schema;
import com.nan.utils.FileManager;

public class CommandsTest {

	@Test(expected = DBAppException.class)
	public void testTableInvalidName() throws DBAppException, DBEngineException {
		// TODO complete to drop table after test

		DBApp database = new DBApp();

		Hashtable<String, String> colNameType = new Hashtable<String, String>();

		colNameType.put("Name", "java.lang.String");
		colNameType.put("ID", "java.lang.Integer");

		Command createTableCommand = new CreateTableCommand(database,
				"Hel*loWorld", colNameType, null, "ID");

		Switch.execute(createTableCommand);

	}

	@Test
	public void testCreateTable() throws DBAppException, DBEngineException {

		DBApp database = new DBApp();

		Hashtable<String, String> colNameType = new Hashtable<String, String>();

		colNameType.put("Name", "java.lang.String");
		colNameType.put("ID", "java.lang.Integer");

		String tableName = "HelloWorld";

		String userDirectory = System.getProperty("user.dir");
		String tablesDirectory = userDirectory + "/data/tables/";

		File tablePath = new File(tablesDirectory + "/" + tableName);

		Command createTableCommand = new CreateTableCommand(database,
				tableName, colNameType, new Hashtable<String, String>(), "ID");

		Schema.init();

		assertFalse("Table folders shouldn't exist before creation",
				tablePath.exists());

		Switch.execute(createTableCommand);

		assertTrue("Table HelloWorld should be created",
				Schema.checkTableExist("HelloWorld"));

		assertTrue(
				"Columns added should be there",
				Schema.checkColExist("HelloWorld", "ID")
						&& Schema.checkColExist("HelloWorld", "Name"));

		assertFalse(
				"Columns belongs to other tables shouldn't be visible",
				Schema.checkColExist("HelloWorld", "Dept")
						&& Schema.checkColExist("HelloWorld", "Location"));

		assertTrue("Table folders should be created", tablePath.exists()
				&& tablePath.isDirectory());

		FileManager.deleteFolder(tablesDirectory);

	}

	@Test(expected = DBAppException.class)
	public void createExistingTable() throws DBAppException, DBEngineException {
		DBApp database = new DBApp();

		Hashtable<String, String> colNameType = new Hashtable<String, String>();

		colNameType.put("Name", "java.lang.String");
		colNameType.put("ID", "java.lang.Integer");

		String tableName = "HelloWorld";

		String userDirectory = System.getProperty("user.dir");
		String tablesDirectory = userDirectory + "/data/tables/";

		Command createTableCommand = new CreateTableCommand(database,
				tableName, colNameType, new Hashtable<String, String>(), "ID");

		Schema.init();

		Switch.execute(createTableCommand);

		FileManager.deleteFolder(tablesDirectory + "/" + tableName);

		Switch.execute(createTableCommand);

		assertFalse("The table's folder shouldn't exist", new File(
				tablesDirectory + "/" + tableName).exists());

	}
}
