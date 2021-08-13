/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class InsertTest {
	@Test
	public void it_converts_to_sql_an_insert_with_only_values() {
		Insert insert = Insert.into("users").values(3);
		assertEquals("INSERT INTO users VALUES (?, ?, ?)", insert.toSQL());
	}

	@Test
	public void it_converts_to_sql_an_insert_with_columns() {
		Insert insert = Insert.into("users").columns("username", "password");
		assertEquals(
				"INSERT INTO users (username, password) VALUES (?, ?)",
				insert.toSQL());
	}

	@Test
	public void it_does_not_convert_to_sql_an_insert_without_values() {
		assertThrows(IllegalStateException.class, () -> {
			Insert insert = Insert.into("users");
			insert.toSQL();
		});
	}

	@Test
	public void it_does_not_convert_to_sql_if_columns_and_values_counts_do_not_match() {
		assertThrows(IllegalStateException.class, () -> {
			Insert.into("users").columns("username", "password").values(3).toSQL();
		});
	}
}
