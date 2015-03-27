package com.nan.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.nan.schema.Page;
import com.nan.schema.indices.DBKDTree;
import com.nan.schema.indices.DBLinearHashTable;

public class ObjectManager {

	public static Page readPage(String tableName, int pageNumber) {
		File[] tablePagesPathes = FileManager
				.getFiles(FileManager.tablesDirectory + "/" + tableName
						+ "/pages");
		File tempFile = new File(tablePagesPathes[pageNumber].toString());
		ObjectInputStream ois;
		Page lastPage = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(tempFile));
			lastPage = (Page) ois.readObject();
			ois.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lastPage;
	}

	public static boolean writePage(String tableName, int pageNumber, Page page) {
		File[] tablePagesPathes = FileManager
				.getFiles(FileManager.tablesDirectory + "/" + tableName
						+ "/pages");
		File tempFile = new File(tablePagesPathes[pageNumber].toString());

		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(tempFile));
			oos.writeObject(page);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean writeIndex(String tableName, String colName,
			DBLinearHashTable index) {
		File tempFile = new File(FileManager.tablesDirectory + "/" + tableName
				+ "/indices/" + colName + ".ser");
		ObjectOutputStream oos;
		try {
			tempFile.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(tempFile));
			oos.writeObject(index);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean writeMultiIndex(String tableName, String colNames,
			DBKDTree multiIndex) {
		File tempFile = new File(FileManager.tablesDirectory + "/" + tableName
				+ "/indices/" + colNames + ".ser");
		ObjectOutputStream oos;
		try {
			tempFile.createNewFile();
			oos = new ObjectOutputStream(new FileOutputStream(tempFile));
			oos.writeObject(multiIndex);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
