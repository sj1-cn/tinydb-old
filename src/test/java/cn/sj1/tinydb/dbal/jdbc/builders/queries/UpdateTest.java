/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnList;

public class UpdateTest {
	private Update update = Update.table("users");

	@Test
	public void it_converts_to_sql_an_update_without_a_where() {
		update.columns("username");
		assertEquals("UPDATE users SET username = ?", update.toSQL());
	}

	@Test
	public void it_converts_to_sql_an_update_with_a_where() {
		update.columns("username").where("user_id = ?");
		assertEquals("UPDATE users SET username = ? WHERE user_id = ?", update.toSQL());
	}

	@Test
	public void it_columns() {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("name VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("description VARCHAR(256)"));
		update = Update.table("user")
			.columns(ColumnList.namesOf(columnList.others()))
			.where(ColumnList.namesOf(columnList.primaryKeys()));
		assertEquals("UPDATE user SET name = ?, description = ? WHERE id = ?", update.toSQL());
	}

	@Test
	public void it_converts_to_sql_an_update_with_several_where_clauses() {
		update.columns("username").where("user_id = ?").orWhere("created_at > ?").where("name LIKE ?");
		assertEquals("UPDATE users SET username = ? WHERE user_id = ? OR created_at > ? AND name LIKE ?",
				update.toSQL());
	}

	@Test
	public void it_converts_to_sql_an_update_with_several_columns_without_a_where() {
		update.columns("username", "password", "role");
		assertEquals("UPDATE users SET username = ?, password = ?, role = ?", update.toSQL());
	}
}
