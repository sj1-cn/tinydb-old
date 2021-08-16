package cn.sj1.tinydb.jdbc.builders.schema;

import static java.sql.JDBCType.BIGINT;
import static java.sql.JDBCType.BINARY;
import static java.sql.JDBCType.BIT;
import static java.sql.JDBCType.BOOLEAN;
import static java.sql.JDBCType.CHAR;
import static java.sql.JDBCType.DATE;
import static java.sql.JDBCType.DECIMAL;
import static java.sql.JDBCType.DOUBLE;
import static java.sql.JDBCType.FLOAT;
import static java.sql.JDBCType.INTEGER;
import static java.sql.JDBCType.LONGVARBINARY;
import static java.sql.JDBCType.LONGVARCHAR;
import static java.sql.JDBCType.NUMERIC;
import static java.sql.JDBCType.REAL;
import static java.sql.JDBCType.SMALLINT;
import static java.sql.JDBCType.TIME;
import static java.sql.JDBCType.TIMESTAMP;
import static java.sql.JDBCType.TINYINT;
import static java.sql.JDBCType.VARBINARY;
import static java.sql.JDBCType.VARCHAR;

import java.sql.JDBCType;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sj1.tinydb.jdbc.builders.schema.JDBC.ColumnType;
import cn.sj1.tinydb.jdbc.builders.schema.ddl.AlterTable;
import cn.sj1.tinydb.jdbc.builders.schema.ddl.AlterTableColumnCommand;

public class MysqlSQLHelper implements SqlHelper {

	public static EnumMap<JDBCType, ColumnType> mapJDBCType2RealColumnTypeName = new EnumMap<>(JDBCType.class);

	public static void regestJDBCType2RealColumnTypeName(JDBCType jdbctype, String columnType) {
		mapJDBCType2RealColumnTypeName.put(jdbctype, ColumnType.valueOf(columnType));
	}

	static {
		regestJDBCType2RealColumnTypeName(CHAR, "CHAR(1)");// precisionInt
		regestJDBCType2RealColumnTypeName(VARCHAR, "VARCHAR(256)");
		regestJDBCType2RealColumnTypeName(LONGVARCHAR, "LONGVARCHAR");// precisionInt
		regestJDBCType2RealColumnTypeName(NUMERIC, "NUMERIC(15,6)");// precisionInt , scaleInt
		regestJDBCType2RealColumnTypeName(DECIMAL, "DECIMAL(15,6)");// precisionInt , scaleInt
		regestJDBCType2RealColumnTypeName(BIT, "BIT");
		regestJDBCType2RealColumnTypeName(BOOLEAN, "BOOLEAN");
		regestJDBCType2RealColumnTypeName(TINYINT, "TINYINT");
		regestJDBCType2RealColumnTypeName(SMALLINT, "SMALLINT");
		regestJDBCType2RealColumnTypeName(INTEGER, "INTEGER");
		regestJDBCType2RealColumnTypeName(BIGINT, "BIGINT");
		regestJDBCType2RealColumnTypeName(REAL, "REAL");
		regestJDBCType2RealColumnTypeName(FLOAT, "FLOAT");// precisionInt
		regestJDBCType2RealColumnTypeName(DOUBLE, "DOUBLE");// precisionInt
		regestJDBCType2RealColumnTypeName(BINARY, "BINARY");
		regestJDBCType2RealColumnTypeName(VARBINARY, "VARBINARY");
		regestJDBCType2RealColumnTypeName(LONGVARBINARY, "LONGVARBINARY");
		regestJDBCType2RealColumnTypeName(DATE, "DATE");
		regestJDBCType2RealColumnTypeName(TIME, "TIME");
//		regestJDBCType2RealColumnTypeName(TIME_WITH_TIMEZONE, "TIME(8)");
		regestJDBCType2RealColumnTypeName(TIMESTAMP, "TIMESTAMP");
//		regestJDBCType2RealColumnTypeName(TIMESTAMP_WITH_TIMEZONE, "TIMESTAMP(26,6)");
	}

	@Override
	public String toSql(String tableName, AlterTableColumnCommand command) {
		String sql = null;
		if (command instanceof AlterTable.ChangeColumnTypeCommand) {
			ColumnDefinition column = command.getColumn();
			sql = String.format("ALTER TABLE %s MODIFY %s %s", tableName, column.getName(), makeColumnType(column));
		} else if (command instanceof AlterTable.AddColumnCommand) {
			ColumnDefinition column = command.getColumn();
			sql = String.format("ALTER TABLE %s ADD COLUMN %s %s", tableName, column.getName(), this.makeColumnType(column));
		} else if (command instanceof AlterTable.DropColumnCommand) {
			sql = String.format("ALTER TABLE %s DROP COLUMN %s", tableName, command.getColumn().getName());
		} else if (command instanceof AlterTable.AlterColumnNullableCommand) {
			String notnull = command.getColumn().getNullable() == ResultSetMetaData.columnNoNulls ? "NOT NULL" : "DEFAULT NULL";
			sql = String.format("ALTER TABLE `%1$s` MODIFY `%2$s` %3$s %4$s", tableName, command.getColumn().getName(), makeColumnType(command.getColumn()), notnull);
		} else if (command instanceof AlterTable.AlterColumnRemarksCommand) {
			sql = String.format("ALTER TABLE `%1$s` CHANGE `%2$s` `%2$s` %3$s COMMENT '%4$s'", tableName, command.getColumn().getName(), makeColumnType(command.getColumn()), command.getColumn().getRemarks().replaceAll("'", "''"));
		}
		return sql;
	}

	private Object makeColumnType(ColumnDefinition column) {
		return typeDefinition(column.getDataType(), column.getColumnSize(), column.getDecimalDigits());
	}

	static enum WithSize {
		WithSize,
		WithPercise,
		WithNothing
	}

	static Map<String, WithSize> withSizes = new HashMap<>();
	static {
		withSizes.put("BIGINT", WithSize.WithNothing);
		withSizes.put("BINARY", WithSize.WithSize);
		withSizes.put("BIT", WithSize.WithSize);
		withSizes.put("BOOLEAN", WithSize.WithNothing);
		withSizes.put("DATE", WithSize.WithNothing);
		withSizes.put("DOUBLE", WithSize.WithNothing);
		withSizes.put("FLOAT", WithSize.WithNothing);
		withSizes.put("INTEGER", WithSize.WithNothing);
		withSizes.put("LONGVARBINARY", WithSize.WithSize);
		withSizes.put("LONGVARCHAR", WithSize.WithSize);
		withSizes.put("DECIMAL", WithSize.WithPercise);
		withSizes.put("NUMERIC", WithSize.WithPercise);
		withSizes.put("REAL", WithSize.WithNothing);
		withSizes.put("SMALLINT", WithSize.WithNothing);
		withSizes.put("TIME", WithSize.WithNothing);
		withSizes.put("TIMESTAMP", WithSize.WithNothing);
		withSizes.put("TINYINT", WithSize.WithNothing);
		withSizes.put("VARBINARY", WithSize.WithSize);
		withSizes.put("VARCHAR", WithSize.WithSize);
	}

	@Override
	public boolean ignoreSize(JDBCType dataType) {
		return ignoreSize(mapJDBCType2RealColumnTypeName.get(dataType).name);
	}

	@Override
	public boolean ignoreSize(String dataType) {
		return withSizes.get(dataType) == WithSize.WithNothing;
	}

	@Override
	public String typeDefinition(JDBCType dataType, int columnSize, int decimalDigits) {
		String definition;
		ColumnType columnType = mapJDBCType2RealColumnTypeName.get(dataType);

		if (VARCHAR == dataType && columnSize > 16000) {
			columnType = new ColumnType("TEXT", columnSize, decimalDigits);
		}

		if (!ignoreSize(columnType.name)) {
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
