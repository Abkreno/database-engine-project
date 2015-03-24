package com.nan;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

public class DBAppTest {
	@Test
	public void testPropertiesReader() throws IOException {
		PropertiesReader p = new PropertiesReader();
		int maximumRowsCountinPage = p.getMaximumRowsCountinPage();
		int bPlusTreeN = p.getBPlusTreeN();
		assertTrue("Maximum Rows Count in Page should be 200",
				maximumRowsCountinPage == 200);
		assertTrue("BPlusTree N should be 20", bPlusTreeN == 20);
	}
}
