package cn.sj1.tinydb.jdbc.builders.schema.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.sj1.tinydb.commons.list.ListMap;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition;
import cn.sj1.tinydb.jdbc.builders.schema.ColumnList;

public class JdbcDababaseMetadata {

	static public JdbcDatabaseInfo getDatabaseInfo(Connection connection) throws SQLException {

		ListMap<Integer, JdbcTypeInfo> types = new ListMap<>(t -> t.dataType);

		DatabaseMetaData meta = connection.getMetaData();
		String databaseProductName = meta.getDatabaseProductName();
		String databaseProductVersion = meta.getDatabaseProductVersion();
		int databaseMajorVersion = meta.getDatabaseMajorVersion();
		int databaseMinorVersion = meta.getDatabaseMinorVersion();
		boolean generatedKeyAlwaysReturned = meta.generatedKeyAlwaysReturned();
		int maxColumnNameLength = meta.getMaxColumnNameLength();

		ResultSet rs = meta.getTypeInfo();

		while (rs.next()) {
			int dataType = rs.getInt("DATA_TYPE");

			String typeName = rs.getString("TYPE_NAME");
			int precition = rs.getInt("PRECISION");
			String literalProfix = rs.getString("LITERAL_PREFIX");
			String literalSuffix = rs.getString("LITERAL_SUFFIX");
			String createParams = rs.getString("CREATE_PARAMS");
			int nullable = rs.getInt("NULLABLE");
			boolean caseSensitive = rs.getBoolean("CASE_SENSITIVE");
			int searchable = rs.getInt("SEARCHABLE");
			short unsignnedAttribute = rs.getShort("UNSIGNED_ATTRIBUTE");
			String fixedPrecScale = rs.getString("FIXED_PREC_SCALE");
			String autoincrment = rs.getString("AUTO_INCREMENT");
			String localTypeName = rs.getString("LOCAL_TYPE_NAME");
			String minimumScale = rs.getString("MINIMUM_SCALE");
			String maxmumScale = rs.getString("MAXIMUM_SCALE");
			String numPrecRadix = rs.getString("NUM_PREC_RADIX");
			JdbcTypeInfo jdbcTypeInfo = new JdbcTypeInfo(typeName, dataType, precition, literalProfix, literalSuffix,
					createParams, nullable, caseSensitive, searchable, unsignnedAttribute, fixedPrecScale, autoincrment,
					localTypeName, minimumScale, maxmumScale, numPrecRadix);
			types.push(jdbcTypeInfo);
		}

		JdbcDatabaseInfo jdbcDatabase = new JdbcDatabaseInfo(databaseProductName, databaseProductVersion,
				databaseMajorVersion, databaseMinorVersion, generatedKeyAlwaysReturned, maxColumnNameLength, types);
		return jdbcDatabase;
	}

	static public ColumnList getColumns(Connection conn, String tableName) throws SQLException {
		ColumnList columnsActual = new ColumnList();
		DatabaseMetaData meta = conn.getMetaData();

		try (ResultSet rs = meta.getColumns(null, null, tableName, null)) {

			while (rs.next()) {
				String columnName = rs.getString("COLUMN_NAME");
				JDBCType dataType = JDBCType.valueOf(rs.getInt("DATA_TYPE"));
				String typeName = rs.getString("TYPE_NAME");
				int columnSize = rs.getInt("COLUMN_SIZE");
				int decimalDigits = rs.getInt("DECIMAL_DIGITS");
				int nullable = rs.getInt("NULLABLE");
				String remarks = rs.getString("REMARKS");
				String defaultValue = rs.getString("COLUMN_DEF");
				int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");
				int ordinalPosition = rs.getInt("ORDINAL_POSITION");
				short sourceDataType = rs.getShort("SOURCE_DATA_TYPE");
				String autoincrment = rs.getString("IS_AUTOINCREMENT");

				ColumnDefinition column = new ColumnDefinition(columnName, dataType, typeName, columnSize,
						decimalDigits, nullable, remarks, defaultValue, charOctetLength, ordinalPosition,
						sourceDataType, autoincrment);
				columnsActual.push(column);
			}

			try (ResultSet primaryKeys = meta.getPrimaryKeys(null, null, tableName)) {
				while (primaryKeys.next()) {
					String columnName = primaryKeys.getString("COLUMN_NAME");
					columnsActual.get(columnName).primarykey();
				}
			}
		}
		return columnsActual;
	}
}