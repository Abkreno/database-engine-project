package com.nan.schema;

import org.junit.Test;

public class SchemaTest {
	@Test
	public void testingSchema() {
		Schema.init();
		Schema.print();
	}

	@Test
	public void testingeSchemaCSV() {
		String[] res = Schema.getSchemaCSV();
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}
	}
}
