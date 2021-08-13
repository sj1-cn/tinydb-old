/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.schema;

import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.INTEGER;
import static cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition.VARCHAR;

import java.util.ArrayList;
import java.util.List;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition;

public class Table implements HasSQLRepresentation {
	private final String name;
	private List<Column> columns;
	private PrimaryKey primaryKey;
	private List<ForeignKey> foreignKeys;
	private boolean ifNotExists = false;

	Table(String name) {
		this.name = name;
		columns = new ArrayList<>();
		foreignKeys = new ArrayList<>();
	}

	public ColumnDefinition string(String name) {
		return string(name, 256);
	}

	public ColumnDefinition string(String name, int length) {
		ColumnDefinition column = VARCHAR(name, length);
		columns.add(column);
		return column;
	}

	public ColumnDefinition integer(String name) {
		ColumnDefinition column = INTEGER(name);
		columns.add(column);
		return column;
	}

	public ColumnDefinition increments(String name) {
		ColumnDefinition id = INTEGER(name)
			.autoIncrement()
			._unsigned()
			.required();
		primaryKey = new PrimaryKey(id);
		columns.add(id);
		return (ColumnDefinition) id;
	}

	public ForeignKey foreign(Column column) {
		ForeignKey foreignKey = new ForeignKey(column);
		foreignKeys.add(foreignKey);
		return foreignKey;
	}

	public PrimaryKey primary(Column... columns) {
		primaryKey = PrimaryKey.composed(columns);
		return primaryKey;
	}

	public Table ifNotExists() {
		ifNotExists = true;
		return this;
	}

	@Override
	public String toSQL() {
		assertPrimaryKeyIsPresent();

        return String.format(
            "CREATE TABLE %s %s (%s %s %s) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;",
            ifNotExistsSQL(),
            name,
            columnDefinitions(),
            primaryKey.toSQL(),
            foreignKeysSQL()
        ).replaceAll("( )+", " ");
	}

	private String ifNotExistsSQL() {
		return ifNotExists ? "IF NOT EXISTS" : "";
	}

	private void assertPrimaryKeyIsPresent() {
		if (primaryKey == null) {
			throw new IllegalStateException("Cannot create table without a primary key");
		}
	}

	private String foreignKeysSQL() {
		StringBuilder sql = new StringBuilder();
        foreignKeys.forEach(
            foreignKey -> sql.append(", ").append(foreignKey.toSQL())
        );
		return sql.toString();
	}

	private String columnDefinitions() {
		StringBuilder definition = new StringBuilder();
        columns.forEach(
            column -> definition.append(column.toSQL()).append(", ")
        );
		return definition.toString().trim();
	}
}
