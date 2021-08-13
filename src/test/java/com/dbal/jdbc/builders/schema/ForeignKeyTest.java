/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.IDENTITY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ForeignKeyTest {
	@Test
	public void it_converts_to_sql_a_foreign_key() {
		ForeignKey key = new ForeignKey(IDENTITY("user_id")).references("id").on("users");
		assertEquals("FOREIGN KEY (user_id) REFERENCES users(id)", key.toSQL());
	}

	@Test
	public void it_fails_to_convert_if_no_table_is_referenced() {
		assertThrows(IllegalStateException.class, () -> {
			ForeignKey key = new ForeignKey(IDENTITY("user_id")).references("id");
			key.toSQL();
		});
	}

	@Test
	public void it_fails_to_convert_if_no_column_is_referenced() {
		assertThrows(IllegalStateException.class, () -> {
			ForeignKey key = new ForeignKey(IDENTITY("user_id")).on("users");
			key.toSQL();
		});
	}
}
