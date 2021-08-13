/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
public class StringColumnTest {
	@Test
	public void it_converts_to_sql_a_string_column_with_a_default_length() {
		assertEquals("username VARCHAR(256)", VARCHAR("username").toSQL());
	}

	@Test
	public void it_converts_to_sql_a_string_column_with_a_specific_length() {
		assertEquals("username VARCHAR(300)", VARCHAR("username").size(300).toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_string_column() {
		assertEquals("username VARCHAR(256) NOT NULL", VARCHAR("username").required().toSQL());
	}

	@Test
	public void it_converts_to_sql_a_string_column_with_a_default_value() {
		assertEquals("favorite_language VARCHAR(256) DEFAULT 'Java'",
				VARCHAR("favorite_language").defaultValue("Java").toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_string_column_with_specific_length_and_default_value() {
		assertEquals("favorite_language VARCHAR(300) NOT NULL DEFAULT 'Java'",
				VARCHAR("favorite_language").size(300).required().defaultValue("Java").toSQL());
	}
}
