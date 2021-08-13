package cn.sj1.nebula.jdbc.builders.schema;

import java.sql.JDBCType;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import cn.sj1.nebula.jdbc.builders.schema.JDBC.ColumnType;
import cn.sj1.nebula.jdbc.builders.schema.ddl.AlterTable;
import cn.sj1.nebula.jdbc.builders.schema.ddl.AlterTableColumnCommand;

public class H2SQLHelper implements SqlHelper {
	@Override
	public String toSql(String tableName, AlterTableColumnCommand command) {
		String sql = null;
		if (command instanceof AlterTable.ChangeColumnTypeCommand) {
			ColumnDefinition column = command.getColumn();
			sql = String.format("ALTER TABLE %s ALTER COLUMN %s %s", tableName, column.getName(), makeColumnType(column));
		} else if (command instanceof AlterTable.AddColumnCommand) {
			ColumnDefinition column = command.getColumn();
			sql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tableName, column.getName(), this.makeColumnType(column));
		} else if (command instanceof AlterTable.DropColumnCommand) {
			sql = String.format("ALTER TABLE %s DROP COLUMN %s", tableName, command.getColumn().getName());
		} else if (command instanceof AlterTable.AlterColumnNullableCommand) {
			if (command.getColumn().getNullable() == ResultSetMetaData.columnNoNulls) {

				sql = String.format("ALTER TABLE %s ALTER COLUMN %s SET NOT NULL", tableName, command.getColumn().getName());
			} else {
				sql = String.format("ALTER TABLE %s ALTER COLUMN %s DROP NOT NULL", tableName, command.getColumn().getName());
			}
		} else if (command instanceof AlterTable.AlterColumnRemarksCommand) {
			sql = String.format("COMMENT ON COLUMN %1$s.%2$s IS '%3$s'", tableName, command.getColumn().getName(), command.getColumn().getRemarks().replaceAll("'", "''"));
		}

		return sql;
	}

	private Object makeColumnType(ColumnDefinition column) {
		return typeDefinition(column.getDataType(), column.getColumnSize(), column.getDecimalDigits());
	}

	@Override
	public String typeDefinition(JDBCType dataType, int columnSize, int decimalDigits) {
		String definition;
		ColumnType columnType = JDBC.mapJDBCType2RealColumnTypeName.get(dataType);

		if (!ignoreSize(dataType)) {
			definition = columnType.name + size(columnSize, decimalDigits);
		} else {
			definition = columnType.name;
		}
		return definition;
	}

	String size(int precision, int scale) {
		if (precision > 0 && scale > 0) {
			return "(" + precision + "," + scale + ")";
		} else if (precision > 0) {
			return "(" + precision + ")";
		} else {
			return "";
		}
	}

	public boolean ignoreSize(JDBCType dataType) {
		return dataType == JDBCType.DATE || dataType == JDBCType.BIGINT || dataType == JDBCType.TIME || dataType == JDBCType.TIMESTAMP || dataType == JDBCType.TIME_WITH_TIMEZONE || dataType == JDBCType.TIMESTAMP_WITH_TIMEZONE
				|| dataType == JDBCType.BOOLEAN;
	}

	@Override
	public String toTypeSQL(ColumnDefinition columnDefinition) {
		List<String> sql = new ArrayList<>();
		sql.add(typeDefinition(columnDefinition.dataType, columnDefinition.columnSize, columnDefinition.decimalDigits));

		if (columnDefinition.unsigned) sql.add("UNSIGNED");
		if ("YES".equals(columnDefinition.autoIncrment)) {
			sql.add("PRIMARY KEY");
			sql.add("AUTO_INCREMENT");
		}
//	if (columnDefinition.primarykey) sql.add("PRIMARY KEY");
//	else if (columnDefinition.nullable == ResultSetMetaData.columnNoNulls) sql.add("NOT NULL");
		if (!columnDefinition.primarykey && columnDefinition.nullable == ResultSetMetaData.columnNoNulls) sql.add("NOT NULL");
		if (columnDefinition.defaultValue != null) sql.add("DEFAULT '" + columnDefinition.defaultValue.replaceAll("'", "''") + "'");

		return String.join(" ", sql);
	}
}
