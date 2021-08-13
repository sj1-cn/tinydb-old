/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DecimalColumnTest {

	@BeforeEach
	public void newColumn() {
	}

	@Test
	public void it_converts_to_sql_an_integer_column() {
		assertEquals("currency DECIMAL(15,6)", DECIMAL("currency").toSQL());
	}

	@Test
	public void it_converts_to_sql_required_integer_column() {
		assertEquals("currency DECIMAL(15,6) NOT NULL", DECIMAL("currency").required().toSQL());
	}

	@Test
	public void it_converts_to_sql_an_unsigned_integer_column() {
		assertEquals("currency DECIMAL(10,6)", DECIMAL("currency").size(10).toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_autoincrementing_integer_column() {
		assertEquals("currency DECIMAL(10,6) NOT NULL", DECIMAL("currency").size(10).required().toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_integer_column_with_a_default() {
		assertEquals("currency DECIMAL(10,6) NOT NULL DEFAULT '1'",
				DECIMAL("currency").size(10).required().defaultValue("1").toSQL());
	}

	@Test
	public void it_converts_to_sql_a_required_unsigned_integer_column_with_a_default_scale() {
		assertEquals("currency DECIMAL(10,3) NOT NULL DEFAULT '1'",
				DECIMAL("currency").size(10).digits(3).required().defaultValue("1").toSQL());
	}
}
