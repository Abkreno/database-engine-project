package com.nan.utils;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class FileManagerTest {
	@Test
	public void testCreateDestroyFolder() {
		String userDir = FileManager.userDirectory;

		String folderPath = userDir + "/test";

		FileManager.createNewFolder(folderPath);

		File f = new File(folderPath);

		assertTrue("The test folder should be created",
				f.exists() && f.isDirectory());

		FileManager.deleteFolder(folderPath);

		assertFalse("The test folder should be deleted", f.exists());

	}

	@Test
	public void testDestroyRecursive() {
		String userDir = FileManager.userDirectory;

		String folderPath = userDir + "/test-recursive";

		FileManager.createNewFolder(folderPath);
		FileManager.createNewFile(folderPath + "/1");

		File f = new File(folderPath);

		File fileInside = new File(folderPath + "/1");

		FileManager.deleteFolder(folderPath);

		assertTrue("test folder should not contain any files",
				f.listFiles().length == 0);
		
		assertFalse("The file inside the folder should be deleted",
				fileInside.exists());

		assertFalse("The test folder should be deleted", f.exists());

	}
}
