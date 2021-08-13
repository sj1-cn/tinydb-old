/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntColumnTest {

	@BeforeEach
	public void newColumn() {
	}

	@Test
	public void it_converts_to_sql_an_integer_column() {
		assertEquals("user_id INTEGER(10)", INTEGER("user_id").toSQL());
	}

	@Test
	public void it_converts_to_sql_required_integer_column() {
		assertEquals("user_id INTEGER(10) NOT NULL", INTEGER("user_id").required().toSQL());
	}

	@Test
	public void it_converts_to_sql_an_autoincrementing_integer_column() {
		assertEquals("user_id INTEGER(10) PRIMARY KEY AUTO_INCREMENT", INTEGER("user_id").autoIncrement().toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_autoincrementing_integer_column() {
		assertEquals("user_id INTEGER(10) PRIMARY KEY AUTO_INCREMENT",
				INTEGER("user_id").autoIncrement().required().toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_integer_column_with_a_default() {
		assertEquals("user_id INTEGER(10) NOT NULL DEFAULT '1'",
				INTEGER("user_id").required().defaultValue("1").toSQL());
	}
}
