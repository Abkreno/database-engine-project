package com.nan;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.nan.utils.PropertiesReader;

public class DBAppTest {
	@Test
	public void testPropertiesReader() throws IOException {
		int maximumRowsCountinPage = Integer.parseInt(PropertiesReader
				.readProperty("MaximumRowsCountinPage"));
		int bPlusTreeN = Integer.parseInt(PropertiesReader
				.readProperty("BPlusTreeN"));
		double LHTLoadFactor = Double.parseDouble(PropertiesReader
				.readProperty("LinearHashTableLF"));
		assertTrue("Maximum Rows Count in Page should be 200",
				maximumRowsCountinPage == 200);
		assertTrue("BPlusTree N should be 20", bPlusTreeN == 20);
		assertTrue("LinearHashTable LoadFactor should be .75f",
				LHTLoadFactor == 0.75f);
	}
}
