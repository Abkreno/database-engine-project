package com.nan.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import com.nan.schema.Column;
import com.nan.schema.Record;
import com.nan.schema.Schema;
import com.nan.schema.Table;

public class FileManager {
	protected static String userDirectory = System.getProperty("user.dir");
	protected static String tablesDirectory = userDirectory + "/data/tables/";
	private static File tempFile;

	public static Hashtable<String, Hashtable<String, Column>> readSchema()
			throws IOException {
		Hashtable<String, Hashtable<String, Column>> schema = new Hashtable<String, Hashtable<String, Column>>();
		File schemaFile = new File(userDirectory + "/data/metadata.csv");
		BufferedReader bf = new BufferedReader(new FileReader(schemaFile));
		String line;
		while ((line = bf.readLine()) != null && line.length() > 0) {
			String[] data = line.split(", ");
			String tableName = data[0];
			if (!schema.containsKey(tableName))
				schema.put(tableName, new Hashtable<String, Column>());
			Hashtable<String, Column> currTableColumns = schema.get(tableName);
			Column currColumn = new Column(data[1], data[2],
					data[3].equals("True"), data[4].equals("True"), data[5]);
			currTableColumns.put(data[1], currColumn);
		}
		bf.close();
		return schema;
	}

	public static Table readTable(String tableName) {
		return null;
	}

	public static void savePage(String tableName, int pageNumber,
			Hashtable<Integer, Record> pageRows) {
	}

	public static void createNewTable(String strTableName) {
		String folderPath = tablesDirectory + strTableName;
		createNewFolder(folderPath);
		createNewFolder(folderPath + "/pages");
		createNewFolder(folderPath + "/indices");
		createNewFile(folderPath + "/indices/indices-info.txt");
		createNewFile(folderPath + "/pages/0.ser");
	}

	public static void createNewFolder(String path) {
		tempFile = new File(path);
		if (!tempFile.exists()) {
			tempFile.mkdirs();
		}
	}

	public static void createNewFile(String path) {
		tempFile = new File(path);
		try {
			tempFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes the table info in the metadata.csv file
	 * 
	 * @param strTableName
	 * @param htblColNameType
	 * @param htblColNameRefs
	 * @param strKeyColName
	 * @throws IOException
	 */
	public static void writeSchema() throws IOException {
		String[] schemaCSV = Schema.getSchemaCSV();
		tempFile = new File(userDirectory + "/data/metadata.csv");
		BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
		for (String curr : schemaCSV) {
			bw.write(curr);
		}
		bw.close();
	}

	public static ArrayList<String> readTableIndicies(String strTableName)
			throws IOException {
		ArrayList<String> result = new ArrayList<String>();
		String indicesFolderPath = tablesDirectory + strTableName
				+ "/indices/indices-info.txt";
		tempFile = new File(indicesFolderPath);
		BufferedReader bf = new BufferedReader(new FileReader(tempFile));
		String line;
		while ((line = bf.readLine()) != null) {
			result.add(line);
		}
		return result;
	}

	public static File[] getFiles(String path) {
		tempFile = new File(path);
		return tempFile.listFiles();
	}

	public static int getPageFilesCount() {
		tempFile = new File(tablesDirectory + "/pages");
		return tempFile.listFiles().length;
	}
}
