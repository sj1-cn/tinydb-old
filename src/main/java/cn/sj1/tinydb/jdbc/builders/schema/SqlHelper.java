package cn.sj1.tinydb.jdbc.builders.schema;

import java.sql.JDBCType;

import cn.sj1.tinydb.jdbc.builders.schema.ddl.AlterTableColumnCommand;

public interface SqlHelper {

	public static final String POSTGRE_SQL_JDBC_DRIVER = "PostgreSQL JDBC Driver";
	public static final String H2_JDBC_DRIVER = "H2 JDBC Driver";

	public static SqlHelper get(String driverName, int majorVersion, int minorVersion) {
		if (driverName.equals(H2_JDBC_DRIVER)) {
			return new H2SQLHelper();
		} else if (driverName.equals(POSTGRE_SQL_JDBC_DRIVER)) {
			return new PostgresqlSQLHelper();
		} else {
			throw new UnsupportedOperationException(String.join(" ", driverName, String.valueOf(majorVersion), String.valueOf(minorVersion)));
		}
	}

	String toTypeSQL(ColumnDefinition columnDefinition);

	String typeDefinition(JDBCType dataType, int columnSize, int decimalDigits);

	String toSql(String tableName, AlterTableColumnCommand command);

}