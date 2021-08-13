/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cn.sj1.tinydb.OrderBy;
import cn.sj1.tinydb.OrderByOp;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnList;

public class SelectTest {
	@Test
	public void it_creates_a_copy_of_a_given_select() {
		select = Select.all().from("users", "u").where("u.username = ?");

		Select copy = new Select(select);
		copy.replaceColumns("COUNT(*)");

		assertEquals("SELECT * FROM users u WHERE u.username = ?", select.toSQL());
		assertEquals("SELECT COUNT(*) FROM users u WHERE u.username = ?", copy.toSQL());
	}

	@Test
	public void it_sets_default_value_for_select_clause() {
		select = Select.all().from("users");
		assertEquals("SELECT * FROM users", select.toSQL());
	}

	@Test
	public void it_columns() {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("name VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("description VARCHAR(256)"));
		select = Select.columns(columnList.all()).from("user").offset(30).limit(10);
		assertEquals("SELECT id, name, description FROM user LIMIT 10 OFFSET 30", select.toSQL());
	}

	@Test
	public void it_where_columns() {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("name VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("description VARCHAR(256)"));
		select = Select.columns(columnList.all()).from("user").where(ColumnList.namesOf(columnList.primaryKeys()));
		assertEquals("SELECT id, name, description FROM user WHERE id = ?", select.toSQL());
	}

	@Test
	public void it_where_columns_orderby() {
		ColumnList columnList = new ColumnList();
		columnList.push(ColumnDefinition.valueOf("id INTEGER(10) PRIMARY KEY"));
		columnList.push(ColumnDefinition.valueOf("name VARCHAR(256)"));
		columnList.push(ColumnDefinition.valueOf("description VARCHAR(256)"));
		select = Select.columns(columnList.all()).from("user").where(ColumnList.namesOf(columnList.primaryKeys())).orderby(OrderBy.empty().andOrderBy("name",OrderByOp.ASC));
		assertEquals("SELECT id, name, description FROM user WHERE id = ? ORDER BY name ASC", select.toSQL());
	}

	@Test
	public void it_selects_specific_columns() {
		select = Select.columns("username", "password").from("users");
		assertEquals("SELECT username, password FROM users", select.toSQL());
	}

	@Test
	public void it_appends_columns_to_select() {
		select = Select.columns("id").addColumns("username", "password").from("users");
		assertEquals("SELECT id, username, password FROM users", select.toSQL());
	}

	@Test
	public void it_adds_an_alias_to_a_query() {
		select = Select.all().from("movies").addTableAlias("m");
		assertEquals("SELECT * FROM movies m", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_count_select_without_joins() {
		select = Select.all().count().from("movies").where("category_id = ?");
		assertEquals("SELECT COUNT(*) FROM movies WHERE category_id = ?", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_count_select_with_joins() {
		select = Select.all()
			.countDistinct("m.id")
			.from("movies")
			.addTableAlias("m")
			.join("movies_categories mc", "m.id = mc.movie_id")
			.where("m.id = ?");
		assertEquals(
				"SELECT COUNT(DISTINCT m.id) FROM movies m INNER JOIN movies_categories mc ON m.id = mc.movie_id WHERE m.id = ?",
				select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_single_where_expression() {
		select = Select.all().from("users").where("username = ?");
		assertEquals("SELECT * FROM users WHERE username = ?", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_several_and_where_expressions() {
		select = Select.all().from("users").where("username = ?").where("password = ?").where("name LIKE ?");
		assertEquals("SELECT * FROM users WHERE username = ? AND password = ? AND name LIKE ?", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_several_or_where_expressions() {
		select = Select.all().from("users").where("username = ?").orWhere("password = ?").orWhere("name LIKE ?");
		assertEquals("SELECT * FROM users WHERE username = ? OR password = ? OR name LIKE ?", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_combination_of_where_expressions() {
		select = Select.all().from("users").where("username = ?").orWhere("password = ?").where("name LIKE ?");
		assertEquals("SELECT * FROM users WHERE username = ? OR password = ? AND name LIKE ?", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_an_in_statement() {
		select = Select.all().from("users").where("username", 2);
		assertEquals("SELECT * FROM users WHERE username IN (?, ?)", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_an_several_in_statement() {
		select = Select.all().from("users").where("username", 2).orWhere("id", 3).where("name", 3);
		assertEquals("SELECT * FROM users WHERE username IN (?, ?) OR id IN (?, ?, ?) AND name IN (?, ?, ?)",
				select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_statement_with_a_limit() {
		select = Select.all().from("users").limit(5);
		assertEquals("SELECT * FROM users LIMIT 5", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_paginated_statement() {
		select = Select.all().from("users").limit(5).offset(5);
		assertEquals("SELECT * FROM users LIMIT 5 OFFSET 5", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_a_join_statement() {
		select = Select.all().from("users", "u").join("roles r", "u.role_id = r.id");
		assertEquals("SELECT * FROM users u INNER JOIN roles r ON u.role_id = r.id", select.toSQL());
	}

	@Test
	public void it_converts_to_sql_several_join_statements() {
		select = Select.all()
			.from("posts", "p")
			.join("posts_tags pt", "pt.post_id = p.id")
			.outerJoin("tags t", "pt.tag_id = t.id");
		assertEquals(
				"SELECT * FROM posts p INNER JOIN posts_tags pt ON pt.post_id = p.id OUTER JOIN tags t ON pt.tag_id = t.id",
				select.toSQL());
	}

	@Test
	public void it_converts_to_sql_several_join_statements_with_a_where_clause() {
		select = Select.all()
			.from("posts", "p")
			.outerJoin("posts_tags pt", "pt.post_id = p.id")
			.join("tags t", "pt.tag_id = t.id")
			.where("p.id = ?");
		assertEquals(
				"SELECT * FROM posts p OUTER JOIN posts_tags pt ON pt.post_id = p.id INNER JOIN tags t ON pt.tag_id = t.id WHERE p.id = ?",
				select.toSQL());
	}

	@Test
	public void it_converts_to_sql_several_join_statements_with_two_where_clauses() {
		select = Select.all()
			.from("posts", "p")
			.join("posts_tags pt", "pt.post_id = p.id")
			.outerJoin("tags t", "pt.tag_id = t.id")
			.where("p.id = ?")
			.where("p.created_at > ?");
		assertEquals(
				"SELECT * FROM posts p INNER JOIN posts_tags pt ON pt.post_id = p.id OUTER JOIN tags t ON pt.tag_id = t.id WHERE p.id = ? AND p.created_at > ?",
				select.toSQL());
	}

	private Select select;
}
