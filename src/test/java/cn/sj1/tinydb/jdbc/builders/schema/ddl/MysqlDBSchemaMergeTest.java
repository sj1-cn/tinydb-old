package cn.sj1.tinydb.jdbc.builders.schema.ddl;

import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.BIGINT;
import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.CHAR;
import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.IDENTITY;
import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import cn.sj1.tinydb.jdbc.TestBase;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnList;
import cn.sj1.tinydb.jdbc.builders.schema.JDBC;
import cn.sj1.tinydb.jdbc.builders.schema.db.JdbcDababaseMetadata;

public class MysqlDBSchemaMergeTest extends TestBase {

	String tableName = "usercomplex".toLowerCase();
	DBSchemaMerge dbSchemaMerge;
	ColumnList columnsPrepared;

	HikariDataSource dataSource;
	Connection conn;

	@BeforeEach
	public void before(TestInfo testInfo) throws SQLException {

		HikariConfig config = new HikariConfig();

		String name = "tinydbtest";
		config.setJdbcUrl("jdbc:mysql://localhost/" + name);
		config.addDataSourceProperty("user", "root");
		config.addDataSourceProperty("password", "MySQL@20210721");

		dataSource = new HikariDataSource(config);
		conn = dataSource.getConnection();

		dbSchemaMerge = new DBSchemaMerge(conn);

		columnsPrepared = new ColumnList();
		columnsPrepared.push(IDENTITY("id"));
		columnsPrepared.push(VARCHAR("name"));
		columnsPrepared.push(DECIMAL("height"));
		columnsPrepared.push(VARCHAR("description"));

		try {
			conn.createStatement().execute("drop table " + tableName + ";");
		} catch (SQLException e) {
		}

		{
			List<String> ls = new ArrayList<>();
			List<String> keys = new ArrayList<>();
			for (ColumnDefinition columnDefinition : columnsPrepared) {
				ls.add(columnDefinition.toDemoSQL());
				if (columnDefinition.isPrimarykey()) {
					keys.add(columnDefinition.getColumnName());
				}
			}
			String sqlCreateTable = JDBC.sql(
					"CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))", tableName,
					String.join(",", ls), String.join(",", keys));

			conn.createStatement().execute(sqlCreateTable);
		}
	}

	@AfterEach
	public void tearDown() throws SQLException {
		dataSource.close();
		conn.close();
	}

	@Test
	public void testCompare_eq() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(0, commandBus.size());
	}

	@Test
	public void testCompare_addColumn() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));
		columnsExpected.push(DECIMAL("age"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof AlterTable.AddColumnCommand);
		assertEquals("age", commandBus.get(0).getColumn().getName());
	}

	@Test
	public void test_prepareMerge_addColumn_prepare() throws SQLException {

		AlterTable.AddColumnCommand addColumn = new AlterTable.AddColumnCommand(VARCHAR("name"));
		Statement statement = mock(Statement.class);

		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		verify(statement).addBatch("ALTER TABLE usercomplex ADD COLUMN name VARCHAR(256)");
	}

	@Test
	public void test_prepareMerge_alterColumn_prepare() throws SQLException {

		AlterTable.ChangeColumnTypeCommand addColumn = new AlterTable.ChangeColumnTypeCommand(VARCHAR("name"));
		Statement statement = mock(Statement.class);

		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		verify(statement).addBatch("ALTER TABLE usercomplex MODIFY name VARCHAR(256)");
	}

	@Test
	public void test_prepareMerge_dropColumn_prepare() throws SQLException {

		AlterTable.DropColumnCommand addColumn = new AlterTable.DropColumnCommand(VARCHAR("name"));
		Statement statement = mock(Statement.class);

		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		verify(statement).addBatch("ALTER TABLE usercomplex DROP COLUMN name");
	}

	@Test
	public void test_merge_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("name").required());// nullables
		columnsExpected.push(VARCHAR("favor").size(1024));// add
		columnsExpected.push(DECIMAL("height").size(32).digits(10));// size and digit
		columnsExpected.push(CHAR("description"));// change type

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNull(columnsActual.get("favor"));
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
			assertNotEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
			assertNotEquals(columnsExpected.get("description").toString(), columnsActual.get("description").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsActual.get("favor").toString(), columnsActual.get("favor").toString());
			assertEquals(columnsExpected.get("height").toString().replaceAll("DECIMAL", "NUMERIC"), columnsActual.get("height").toString().replaceAll("DECIMAL", "NUMERIC"));
			assertEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
			assertEquals(columnsExpected.get("description").toString(), columnsActual.get("description").toString());
		}
	}

	@Test
	public void test_merge_addColumn_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("favor").size(1024));

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNull(columnsActual.get("favor"));
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsActual.get("favor").toString(), columnsActual.get("favor").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_type_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(BIGINT("height"));

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height"), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_size_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(DECIMAL("height").size(32));

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString().replaceAll("DECIMAL", "NUMERIC"), columnsActual.get("height").toString().replaceAll("DECIMAL", "NUMERIC"));
		}
	}

	@Test
	public void test_merge_alterColumn_change_digit_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(DECIMAL("height").size(32).digits(10));

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString().replaceAll("DECIMAL", "NUMERIC"), columnsActual.get("height").toString().replaceAll("DECIMAL", "NUMERIC"));
		}
	}

	@Test
	public void test_merge_alterColumn_change_remarks_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("name").remarks("person's name"));

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_merge_alterColumn_change_size_digit_remarks_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(DECIMAL("height").size(50).digits(10).required().remarks("person's height"));

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("height").toString(), columnsActual.get("height").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("height").toString().replaceAll("DECIMAL", "NUMERIC"), columnsActual.get("height").toString().replaceAll("DECIMAL", "NUMERIC"));
		}
	}

	@Test
	public void test_merge_alterColumn_change_nullable_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.push(VARCHAR("name").required());

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(columnsExpected.get("name").toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_merge_dropColumn_exec() throws SQLException {
		ColumnList columnsExpected = columnsPrepared.copy();
		columnsExpected.remove("name");

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotNull(columnsActual.get("name"));
		}

		dbSchemaMerge.merge(conn, tableName, columnsExpected);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNull(columnsActual.get("name"));
		}
	}

	@Test
	public void test_prepareMerge_addColumn_exec() throws SQLException {

		AlterTable.AddColumnCommand addColumn = new AlterTable.AddColumnCommand(VARCHAR("favor").size(1024));
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNull(columnsActual.get("favor"));
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(addColumn.getColumn().toString(), columnsActual.get("favor").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_type_exec() throws SQLException {

		AlterTable.ChangeColumnTypeCommand alterColumn = new AlterTable.ChangeColumnTypeCommand(BIGINT("height"));
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_size_exec() throws SQLException {

		AlterTable.ChangeColumnTypeCommand alterColumn = new AlterTable.ChangeColumnTypeCommand(DECIMAL("height").size(32));
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString().replaceAll("DECIMAL", "NUMERIC"), columnsActual.get("height").toString().replaceAll("DECIMAL", "NUMERIC"));
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_digit_exec() throws SQLException {

		AlterTable.ChangeColumnTypeCommand alterColumn = new AlterTable.ChangeColumnTypeCommand(
				DECIMAL("height").size(32).digits(10));
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("height").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString().replaceAll("DECIMAL", "NUMERIC"), columnsActual.get("height").toString().replaceAll("DECIMAL", "NUMERIC"));
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_remarks_exec() throws SQLException {

		AlterTable.AlterColumnRemarksCommand alterColumn = new AlterTable.AlterColumnRemarksCommand(
				VARCHAR("name").remarks("person's name"));
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_prepareMerge_alterColumn_change_nullable_exec() throws SQLException {

		AlterTable.AlterColumnNullableCommand alterColumn = new AlterTable.AlterColumnNullableCommand(
				VARCHAR("name").required());
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, alterColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertEquals(alterColumn.getColumn().toString(), columnsActual.get("name").toString());
		}
	}

	@Test
	public void test_prepareMerge_dropColumn_exec() throws SQLException {

		AlterTable.DropColumnCommand addColumn = new AlterTable.DropColumnCommand(VARCHAR("name").size(1024));
		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNotNull(columnsActual.get("name"));
		}

		Statement statement = conn.createStatement();
		dbSchemaMerge.prepareMerge(statement, tableName, addColumn);
		int[] results = statement.executeBatch();
		assertEquals(1, results.length);
		assertEquals(0, results[0]);

		{
			ColumnList columnsActual = JdbcDababaseMetadata.getColumns(conn, tableName);
			assertNull(columnsActual.get("name"));
		}
	}

	@Test
	public void testCompare_change_type() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(BIGINT("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof AlterTable.ChangeColumnTypeCommand);
		assertEquals("height", commandBus.get(0).getColumn().getName());
		assertEquals(JDBCType.BIGINT, commandBus.get(0).getColumn().getDataType());
	}

	@Test
	public void testCompare_change_size() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name").size(1024));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof AlterTable.ChangeColumnTypeCommand);
		assertEquals("name", commandBus.get(0).getColumn().getName());
		assertEquals(1024, commandBus.get(0).getColumn().getColumnSize());
	}

	@Test
	public void testCompare_change_digit() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height").size(20).digits(2));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof AlterTable.ChangeColumnTypeCommand);
		assertEquals("height", commandBus.get(0).getColumn().getName());
		assertEquals(20, commandBus.get(0).getColumn().getColumnSize());
		assertEquals(2, commandBus.get(0).getColumn().getDecimalDigits());
	}

	@Test
	public void testCompare_change_remark() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name").remarks("name's Remark"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertTrue(commandBus.get(0) instanceof AlterTable.AlterColumnRemarksCommand);
		assertEquals("AlterColumnRemarksCommand [name VARCHAR(256) REMARKS 'name''s Remark']",
				commandBus.get(0).toString());
		assertEquals("name's Remark", commandBus.get(0).getColumn().getRemarks());
	}

	@Test
	public void testCompare_change_nullable() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
		columnsExpected.push(VARCHAR("name").required());
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertEquals("AlterColumnNullableCommand [name VARCHAR(256) NOT NULL]", commandBus.get(0).toString());
	}

	@Test
	public void testCompare_change_remove_column() throws SQLException {

		ColumnList columnsExpected = new ColumnList();
		columnsExpected.push(IDENTITY("id"));
//		columnsExpected.push(VARCHAR("name"));
		columnsExpected.push(DECIMAL("height"));
		columnsExpected.push(VARCHAR("description"));

		ColumnList actual = JdbcDababaseMetadata.getColumns(conn, tableName);

		List<AlterTableColumnCommand> commandBus = dbSchemaMerge.compare(columnsExpected, actual,true);

		assertEquals(1, commandBus.size());
		assertEquals("DropColumnCommand [name VARCHAR(256)]", commandBus.get(0).toString());
	}

	@Test
	public void testGetCurrentActualColumns() throws SQLException {
		List<ColumnDefinition> actual = JdbcDababaseMetadata.getColumns(conn, tableName).list();
		List<?> expected = columnsPrepared.list();
		assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).toString().replaceAll("DECIMAL", "NUMERIC"), actual.get(i).toString().replaceAll("DECIMAL", "NUMERIC"));
		}
	}

}
