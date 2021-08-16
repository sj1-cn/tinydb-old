/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColumnsTest {
	@Test
	public void it_converts_to_sql_a_single_column() {
		columns.add("username");
		assertEquals("username", columns.toDemoSQL());
	}

//	@SuppressWarnings("deprecation")
//	@Test
//	public void it_converts_to_sql_the_wildcard_for_all_columns() {
//		assertThat(Columns.all().toSQL(), is("*"));
//	}

	@Test
	public void it_converts_to_sql_several_columns() {
		columns.add("username", "password", "email");

		assertEquals("username, password, email", columns.toDemoSQL());
	}

	@Test
	public void it_replaces_existing_columns() {
		columns.add("username", "password").clear().add("password", "email");

		assertEquals("password, email", columns.toDemoSQL());
	}

//	@SuppressWarnings("deprecation")
//	@Test
//	public void it_selects_the_count_of_rows() {
//		columns.count();
//
//		assertThat(columns.toSQL(), is("COUNT(*)"));
//	}

//	@SuppressWarnings("deprecation")
//	@Test
//	public void it_selects_the_count_of_distinct_rows() {
//		columns.countDistinct("u.id");
//
//		assertThat(columns.toSQL(), is("COUNT(DISTINCT u.id)"));
//	}

	private Columns columns = Columns.empty();
}
