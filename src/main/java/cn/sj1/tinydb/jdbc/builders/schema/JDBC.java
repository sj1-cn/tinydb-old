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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import cn.sj1.tinydb.jdbc.builders.schema.ddl.DBSchemaMerge;

public class JDBC {
	public static Timestamp timestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	private static EnumMap<JDBCType, String> mapJDBCType2JavaClazz = new EnumMap<>(JDBCType.class);
	private static Map<String, JDBCType> mapJavaClass2JDBCType = new HashMap<>();

	public static JDBCType jdbcType(String clazz) {
		return mapJavaClass2JDBCType.get(clazz);
	}

	public static JDBCType jdbcType(Class<?> clazz) {
		return mapJavaClass2JDBCType.get(clazz.getName());
	}

	static {
		mapJDBCType2JavaClazz.put(CHAR, "java.lang.String");
		mapJDBCType2JavaClazz.put(VARCHAR, "java.lang.String");
		mapJDBCType2JavaClazz.put(LONGVARCHAR, "java.lang.String");
		mapJDBCType2JavaClazz.put(NUMERIC, "java.math.BigDecimal");
		mapJDBCType2JavaClazz.put(DECIMAL, "java.math.BigDecimal");
		mapJDBCType2JavaClazz.put(BOOLEAN, "boolean");
		mapJDBCType2JavaClazz.put(BIT, "boolean");
		mapJDBCType2JavaClazz.put(TINYINT, "byte");
		mapJDBCType2JavaClazz.put(SMALLINT, "short");
		mapJDBCType2JavaClazz.put(INTEGER, "int");
		mapJDBCType2JavaClazz.put(BIGINT, "long");
		mapJDBCType2JavaClazz.put(REAL, "float");
		mapJDBCType2JavaClazz.put(FLOAT, "double");
		mapJDBCType2JavaClazz.put(DOUBLE, "double");
		mapJDBCType2JavaClazz.put(BINARY, "bytes");
		mapJDBCType2JavaClazz.put(VARBINARY, "bytes");
		mapJDBCType2JavaClazz.put(LONGVARBINARY, "bytes");
		mapJDBCType2JavaClazz.put(DATE, "java.sql.Date");
		mapJDBCType2JavaClazz.put(TIME, "java.sql.Time");
//		mapJDBCType2JavaClazz.put(TIME_WITH_TIMEZONE, "java.sql.Time");
		mapJDBCType2JavaClazz.put(TIMESTAMP, "java.sql.Timestamp");
//		mapJDBCType2JavaClazz.put(TIMESTAMP_WITH_TIMEZONE, "java.sql.Timestamp");

		mapJavaClass2JDBCType.put("java.lang.String", VARCHAR);
		mapJavaClass2JDBCType.put("java.math.BigDecimal", DECIMAL);
		mapJavaClass2JDBCType.put("boolean", BOOLEAN);
		mapJavaClass2JDBCType.put("char", CHAR);
		mapJavaClass2JDBCType.put("byte", TINYINT);
		mapJavaClass2JDBCType.put("short", SMALLINT);
		mapJavaClass2JDBCType.put("int", INTEGER);
		mapJavaClass2JDBCType.put("long", BIGINT);
		mapJavaClass2JDBCType.put("float", REAL);
		mapJavaClass2JDBCType.put("double", DOUBLE);
		mapJavaClass2JDBCType.put("bytes", BINARY);
		mapJavaClass2JDBCType.put("java.sql.Date", DATE);
		mapJavaClass2JDBCType.put("java.sql.Time", TIME);
		mapJavaClass2JDBCType.put("java.sql.Timestamp", TIMESTAMP);

		mapJavaClass2JDBCType.put("java.lang.Boolean", BOOLEAN);
		mapJavaClass2JDBCType.put("java.lang.Character", VARCHAR);
		mapJavaClass2JDBCType.put("java.lang.Byte", TINYINT);
		mapJavaClass2JDBCType.put("java.lang.Short", SMALLINT);
		mapJavaClass2JDBCType.put("java.lang.Integer", INTEGER);
		mapJavaClass2JDBCType.put("java.lang.Long", BIGINT);
		mapJavaClass2JDBCType.put("java.lang.Float", REAL);
		mapJavaClass2JDBCType.put("java.lang.Double", DOUBLE);
		mapJavaClass2JDBCType.put("java.lang.Date", DATE);
	}

	public static JdbcMapping map(Class<?> pojoClazz) {
		return mapJavaClazz2JdbcMapping.get(pojoClazz.getName());
	}

	static class ColumnType {
		final String name;
		final int size;
		final int digit;

		public ColumnType(String name, int size, int digit) {
			this.name = name;
			this.size = size;
			this.digit = digit;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ColumnDefault [name=");
			builder.append(name);
			builder.append(", size=");
			builder.append(size);
			builder.append(", digit=");
			builder.append(digit);
			builder.append("]");
			return builder.toString();
		}

		static ColumnType valueOf(String define) {
			int begin = define.indexOf('(');
			String name;
			int size;
			int digit;
			if (begin > 0) {
				name = define.substring(0, begin);
				int com = define.indexOf(',', begin);
				if (com > 0) {
					size = Integer.parseInt(define.substring(begin + 1, com));
					digit = Integer.parseInt(define.substring(com + 1, define.length() - 1));
				} else {
					size = Integer.parseInt(define.substring(begin + 1, define.length() - 1));
					digit = 0;
				}
			} else {
				name = define;
				size = 0;
				digit = 0;
			}
			return new ColumnType(name, size, digit);
		}
	}

	@Deprecated
	static public String typeDefinition(JDBCType dataType, int columnSize, int decimalDigits) {
		String definition;
		ColumnType columnType = JDBC.mapJDBCType2RealColumnTypeName.get(dataType);

		if (!ignoreSize(dataType)) {
			definition = columnType.name + size(columnSize, decimalDigits);
		} else {
			definition = columnType.name;
		}
		return definition;
	}

	@Deprecated
	static String size(int precision, int scale) {
		if (precision > 0 && scale > 0) {
			return "(" + precision + "," + scale + ")";
		} else if (precision > 0) {
			return "(" + precision + ")";
		} else {
			return "";
		}
	}

	 public static boolean ignoreSize(JDBCType dataType) {
	 	return dataType == JDBCType.DATE || dataType == JDBCType.BIGINT || dataType == JDBCType.TIME || dataType == JDBCType.TIMESTAMP || dataType == JDBCType.TIME_WITH_TIMEZONE || dataType == JDBCType.TIMESTAMP_WITH_TIMEZONE
	 			|| dataType == JDBCType.BOOLEAN;
	 }

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
		regestJDBCType2RealColumnTypeName(TINYINT, "TINYINT(3)");
		regestJDBCType2RealColumnTypeName(SMALLINT, "SMALLINT(5)");
		regestJDBCType2RealColumnTypeName(INTEGER, "INTEGER(10)");
		regestJDBCType2RealColumnTypeName(BIGINT, "BIGINT(19)");
		regestJDBCType2RealColumnTypeName(REAL, "REAL(7)");
		regestJDBCType2RealColumnTypeName(FLOAT, "FLOAT(7)");// precisionInt
		regestJDBCType2RealColumnTypeName(DOUBLE, "DOUBLE(17)");// precisionInt
		regestJDBCType2RealColumnTypeName(BINARY, "BINARY");
		regestJDBCType2RealColumnTypeName(VARBINARY, "VARBINARY");
		regestJDBCType2RealColumnTypeName(LONGVARBINARY, "LONGVARBINARY");
		regestJDBCType2RealColumnTypeName(DATE, "DATE");
		regestJDBCType2RealColumnTypeName(TIME, "TIME");
//		regestJDBCType2RealColumnTypeName(TIME_WITH_TIMEZONE, "TIME(8)");
		regestJDBCType2RealColumnTypeName(TIMESTAMP, "TIMESTAMP");
//		regestJDBCType2RealColumnTypeName(TIMESTAMP_WITH_TIMEZONE, "TIMESTAMP(26,6)");
	}

	public static class JdbcMapping {
		public String typename;
		public String getname;
		public String setName;
		public Class<?> jdbcClazz;

		public JdbcMapping(String typename, String getname, String setname, Class<?> getclazz) {
			this.typename = typename;
			this.getname = getname;
			this.setName = setname;
			this.jdbcClazz = getclazz;
		}
	}

	private static Map<String, JdbcMapping> mapJavaClazz2JdbcMapping = new HashMap<>();

	static void rigester(Class<?> clazz, JdbcMapping jdbctype) {
		mapJavaClazz2JdbcMapping.put(clazz.getName(), jdbctype);
	}

	static void rigester(String clazz, JdbcMapping jdbctype) {
		mapJavaClazz2JdbcMapping.put(clazz, jdbctype);
	}

	static {
		// @formatter:off
		rigester(String.class, new JdbcMapping("String", "getString","setString", String.class));
		rigester(boolean.class, new JdbcMapping("boolean", "getBoolean", "setBoolean", boolean.class));
		rigester(char.class, new JdbcMapping("String", "getString","setString", String.class));
		rigester(byte.class, new JdbcMapping("byte", "getByte", "setByte", byte.class));
		rigester(short.class, new JdbcMapping("short", "getShort",  "setShort", short.class));
		rigester(int.class, new JdbcMapping("int", "getInt", "setInt",int.class));
		rigester(long.class, new JdbcMapping("long", "getLong","setLong", long.class));
		rigester(float.class, new JdbcMapping("float", "getFloat", "setFloat",float.class));
		rigester(double.class, new JdbcMapping("double", "getDouble", "setDouble",  double.class));
		rigester("bytes", new JdbcMapping("bytes", "getBytes", "setBytes",  byte[].class));// byte[]
		rigester(java.sql.Date.class, new JdbcMapping("java.sql.Date", "getDate","setDate", java.sql.Date.class));// java.sql.Date
		rigester(java.sql.Time.class, new JdbcMapping("Time", "getTime", "setTime", java.sql.Time.class));// java.sql.Time
		rigester(java.sql.Timestamp.class, new JdbcMapping("Timestamp", "getTimestamp","setTimestamp", java.sql.Timestamp.class));// java.sql.Timestamp
		rigester(java.math.BigDecimal.class, new JdbcMapping("java.math.BigDecimal", "getBigDecimal", "setBigDecimal",BigDecimal.class));// java.math.BigDecimal

		rigester(String.class, new JdbcMapping("String", "getString","setString", String.class));
		rigester(Character.class, new JdbcMapping("String", "getString","setString", String.class));
		rigester(Boolean.class, new JdbcMapping("boolean", "getBoolean", "setBoolean", boolean.class));
		rigester(Byte.class, new JdbcMapping("byte", "getByte", "setByte", byte.class));
		rigester(Short.class, new JdbcMapping("short", "getShort",  "setShort", short.class));
		rigester(Integer.class, new JdbcMapping("int", "getInt", "setInt",int.class));
		rigester(Long.class, new JdbcMapping("long", "getLong","setLong", long.class));
		rigester(Float.class, new JdbcMapping("float", "getFloat", "setFloat",float.class));
		rigester(Double.class, new JdbcMapping("double", "getDouble", "setDouble",  double.class));
		// @formatter:on
	}

	static Map<String, String> sqlMaps = new HashMap<>();

	public static void registerSQL(String key, String fommatter) {
		sqlMaps.put(key, fommatter);
	}

	public static String getSql(String key) {
		return sqlMaps.get(key);
	}

	public static String sql(String key, Object... objects) {
		return String.format(sqlMaps.get(key), objects);
	}

	static {
//				ADD [ COLUMN ] column data_type [ COLLATE collation ] [ column_constraint [ ... ] ]
//	    	    DROP [ COLUMN ] [ IF EXISTS ] column [ RESTRICT | CASCADE ]
//	    	    ALTER [ COLUMN ] column [ SET DATA ] TYPE data_type [ COLLATE collation ] [ USING expression ]
//	    	    ALTER [ COLUMN ] column SET DEFAULT expression
//	    	    ALTER [ COLUMN ] column DROP DEFAULT
//	    	    ALTER [ COLUMN ] column { SET | DROP } NOT NULL
//	    	    ALTER [ COLUMN ] column SET STATISTICS integer
//	    	    ALTER [ COLUMN ] column SET ( attribute_option = value [, ... ] )
//	    	    ALTER [ COLUMN ] column RESET ( attribute_option [, ... ] )
//	    	    ALTER [ COLUMN ] column SET STORAGE { PLAIN | EXTERNAL | EXTENDED | MAIN }

		// @formatter:off
		JDBC.registerSQL("ALTER TABLE ${tablename} ADD COLUMN ${columnname} ${columndefinition}","ALTER TABLE %1$s ADD COLUMN %2$s  TYPE %3$s");
		JDBC.registerSQL("ALTER TABLE ${tablename} ALTER COLUMN ${columnname} ${columndefinition}", "ALTER TABLE %1$s ALTER COLUMN %2$s TYPE %3$s");
		JDBC.registerSQL("ALTER TABLE ${tablename} ALTER COLUMN ${oldname} RENAME TO ${newname}", "ALTER TABLE %1$s ALTER COLUMN %2$s RENAME TO %3$s");
		JDBC.registerSQL("ALTER TABLE ${tablename} ALTER COLUMN ${oldname} SET ${nullable}", "ALTER TABLE %1$s ALTER COLUMN %2$s SET %3$s");
		JDBC.registerSQL("ALTER TABLE ${tablename} ALTER COLUMN ${columnname} REMARKS ${remarks}", "COMMENT ON COLUMN %1$s.%2$s IS '%3$s'");
		JDBC.registerSQL("ALTER TABLE ${tablename} ADD PRIMARY KEY ${columnname}", "ALTER TABLE %1$s ADD PRIMARY KEY (%2$s)");
		JDBC.registerSQL("ALTER TABLE ${tablename} DROP COLUMN ${columnname}", "ALTER TABLE %1$s DROP COLUMN %2$s");

		JDBC.registerSQL("CREATE TABLE ${tablename}(${columndefinitions}))", "CREATE TABLE %1$s(%2$s)");
		JDBC.registerSQL("CREATE TABLE ${tablename}(${columndefinitions},PRIMARY KEY(${keys}))", "CREATE TABLE %1$s(%2$s,PRIMARY KEY(%3$s))");
		JDBC.registerSQL("SELECT ${columns} FROM ${tablename}", "SELECT %1$s FROM %2$s");
		JDBC.registerSQL("SELECT ${columns} FROM ${tablename} WHERE ${causes}", "SELECT %1$s FROM %2$s WHERE %3$s");
		JDBC.registerSQL("INSERT INTO ${tablename}(${columns}) VALUES(${values})","INSERT INTO %1$s(%2$s) VALUES(%3$s)");
		JDBC.registerSQL("UPDATE ${tablename} SET ${setvalues} WHERE ${causes}", "UPDATE %1$s SET %2$s WHERE %3$s");
		JDBC.registerSQL("DELETE ${tablename} WHERE ${causes}", "DELETE %1$s WHERE %2$s");

		// @formatter:on
	}

	public static boolean mergeIfExists(Connection conn, String tableName, ColumnList columnsExpected) {
		try {
			return new DBSchemaMerge(conn).merge(conn, tableName, columnsExpected);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
