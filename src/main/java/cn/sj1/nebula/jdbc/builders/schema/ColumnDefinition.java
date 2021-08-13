package cn.sj1.nebula.jdbc.builders.schema;

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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dbal.jdbc.builders.schema.Column;

import cn.sj1.nebula.jdbc.builders.schema.JDBC.ColumnType;

public class ColumnDefinition implements Column {
	private static final String YES = "YES";
	private static final String NO = "NO";
	String columnName;
	JDBCType dataType;
	String typeName;
	int columnSize;
	int decimalDigits;
	int nullable = ResultSetMetaData.columnNullable;
	String remarks = "";
	String defaultValue;
	int charOctetLength;
	int ordinalPosition;
	short sourceDataType;
	String autoIncrment = NO;
//	String generatedColumn = "NO";

	boolean unsigned = false;
	boolean primarykey = false;

	public ColumnDefinition(String name, JDBCType datatype) {
		this.columnName = name;
		this.dataType = datatype;

		ColumnType columnType = JDBC.mapJDBCType2RealColumnTypeName.get(dataType);
		this.columnSize = columnType.size;
		this.decimalDigits = columnType.digit;
	}

	public ColumnDefinition(String name, JDBCType datatype, int size) {
		this.columnName = name;
		this.dataType = datatype;
		this.columnSize = size;
		this.decimalDigits = 0;
	}

	public ColumnDefinition(String columnName, JDBCType dataType, String typeName, int columnSize, int decimalDigits,
			int nullable, String remarks, String defaultValue, int charOctetLength, int ordinalPosition,
			short sourceDataType, String autoIncrment) {
		this.columnName = columnName;
		this.dataType = dataType;
		this.typeName = typeName;
		this.columnSize = columnSize;
		this.decimalDigits = decimalDigits;
		this.nullable = nullable;
		this.remarks = remarks;
		this.defaultValue = defaultValue;
		this.charOctetLength = charOctetLength;
		this.ordinalPosition = ordinalPosition;
		this.sourceDataType = sourceDataType;
		this.autoIncrment = YES.equals(autoIncrment) ? YES : NO;
	}

	public ColumnDefinition size(int size) {
		this.columnSize = size;
		return this;
	}

	public ColumnDefinition digits(int digits) {
		this.decimalDigits = digits;
		return this;
	}

	public ColumnDefinition remarks(String remarks) {
		this.remarks = remarks;
		return this;
	}

	public ColumnDefinition required() {
		this.nullable = ResultSetMetaData.columnNoNulls;
		return this;
	}

	public ColumnDefinition primarykey() {
		this.primarykey = true;
		this.nullable = ResultSetMetaData.columnNoNulls;
		return this;
	}

	public ColumnDefinition required(boolean required) {
		this.nullable = required ? ResultSetMetaData.columnNoNulls : ResultSetMetaData.columnNullable;
		return this;
	}

	public ColumnDefinition defaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}

	public ColumnDefinition autoIncrement() {
		primarykey = true;
		autoIncrment = YES;
		return this;
	}

	public static boolean ignoreSize(JDBCType dataType) {
		return dataType == JDBCType.DATE || dataType == JDBCType.TIME || dataType == JDBCType.TIMESTAMP
				|| dataType == JDBCType.TIME_WITH_TIMEZONE || dataType == JDBCType.TIMESTAMP_WITH_TIMEZONE
				|| dataType == JDBCType.BOOLEAN;
	}

	private static String size(int precision, int scale) {
		if (precision > 0 && scale > 0) {
			return "(" + precision + "," + scale + ")";
		} else if (precision > 0) {
			return "(" + precision + ")";
		} else {
			return "";
		}
	}

	@Override
	public String toString() {

		List<String> sql = new ArrayList<>();
		sql.add(this.columnName);

		String definition;
		if (!ignoreSize(dataType)) {
			definition = dataType.getName() + size(columnSize, decimalDigits);
		} else {
			definition = dataType.getName();
		}
		sql.add(definition);

		if (this.primarykey) {
			sql.add("PRIMARY KEY");
			if (YES.equals(this.autoIncrment)) sql.add("AUTO_INCREMENT");
		} else if (this.nullable == ResultSetMetaData.columnNoNulls) sql.add("NOT NULL");
		if (this.defaultValue != null) sql.add("DEFAULT '" + this.defaultValue.replace("'", "''") + "'");
		if (this.remarks != null && !this.remarks.isEmpty()) sql.add("REMARKS '" + this.remarks.replace("'", "''") + "'");

//		if (this.unsigned) sql.add("UNSIGNED");
//		if (!typeName.equals(dataType.getName())) {
//			builder.append(", typeName=");
//			builder.append(typeName);
//		}
//
//		if (this.remarks != null && this.remarks.length() > 0) {
//			builder.append(", remarks=");
//			builder.append(remarks);
//		}
//		if (this.defaultValue != null) {
//			builder.append(", defaultValue=");
//			builder.append(defaultValue);
//		}
//		builder.append(", charOctetLength=");
//		builder.append(charOctetLength);
//		builder.append(", ordinalPosition=");
//		builder.append(ordinalPosition);

//		if (this.sourceDataType != 0) {
//			builder.append(", sourceDataType=");
//			builder.append(sourceDataType);
//		}
//		if (this.autoIncrment != "NO") {
//			builder.append(", autoIncrment=");
//			builder.append(autoIncrment);
//		}
//		builder.append(", unsigned=");
//		builder.append(unsigned);
//		builder.append(", primarykey=");
//		builder.append(primarykey);
//		builder.append(",");
		return String.join(" ", sql);
	}

	//
//	    @Override
	public String toSQL() {
		List<String> sql = new ArrayList<>();
		sql.add(this.columnName);

		sql.add(JDBC.typeDefinition(this.dataType, columnSize, decimalDigits));

		if (this.unsigned) sql.add("UNSIGNED");
		if (YES.equals(this.autoIncrment)) {
			sql.add("PRIMARY KEY");
			sql.add("AUTO_INCREMENT");
		}
//		if (this.primarykey) sql.add("PRIMARY KEY");
//		else if (this.nullable == ResultSetMetaData.columnNoNulls) sql.add("NOT NULL");
		if (!this.primarykey && this.nullable == ResultSetMetaData.columnNoNulls) sql.add("NOT NULL");
		if (this.defaultValue != null) sql.add("DEFAULT '" + this.defaultValue.replaceAll("'", "''") + "'");

		return String.join(" ", sql);
	}

	@Override
	public String getName() {
		return this.columnName;
	}

	public ColumnDefinition _unsigned() {
		this.unsigned = true;
		return this;
	}

	static public ColumnDefinition CHAR(String name) {
		return new ColumnDefinition(name, CHAR);
	}

	static public ColumnDefinition VARCHAR(String name) {
		return new ColumnDefinition(name, VARCHAR, 256);
	}

	static public ColumnDefinition VARCHAR(String name, int length) {
		return new ColumnDefinition(name, VARCHAR, length);
	}

	static public ColumnDefinition LONGVARCHAR(String name) {
		return new ColumnDefinition(name, LONGVARCHAR);
	}

	static public ColumnDefinition Column(JDBCType type, String name) {
		return new ColumnDefinition(name, type);
	}

	static public ColumnDefinition NUMERIC(String name) {
		return new ColumnDefinition(name, NUMERIC);
	}

	static public ColumnDefinition DECIMAL(String name) {
		return new ColumnDefinition(name, DECIMAL);
	}

	static public ColumnDefinition IDENTITY(String name) {
		return new ColumnDefinition(name, BIGINT).primarykey();
	}

	static public ColumnDefinition BIT(String name) {
		return new ColumnDefinition(name, BIT);
	}

	static public ColumnDefinition BOOLEAN(String name) {
		return new ColumnDefinition(name, BOOLEAN);
	}

	static public ColumnDefinition TINYINT(String name) {
		return new ColumnDefinition(name, TINYINT);
	}

	static public ColumnDefinition SMALLINT(String name) {
		return new ColumnDefinition(name, SMALLINT);
	}

	static public ColumnDefinition INTEGER(String name) {
		return new ColumnDefinition(name, INTEGER);
	}

	static public ColumnDefinition BIGINT(String name) {
		return new ColumnDefinition(name, BIGINT);
	}

	static public ColumnDefinition REAL(String name) {
		return new ColumnDefinition(name, REAL);
	}

	static public ColumnDefinition FLOAT(String name) {
		return new ColumnDefinition(name, FLOAT);
	}

	static public ColumnDefinition DOUBLE(String name) {
		return new ColumnDefinition(name, DOUBLE);
	}

	static public ColumnDefinition BINARY(String name) {
		return new ColumnDefinition(name, BINARY);
	}

	static public ColumnDefinition VARBINARY(String name) {
		return new ColumnDefinition(name, VARBINARY);
	}

	static public ColumnDefinition LONGVARBINARY(String name) {
		return new ColumnDefinition(name, LONGVARBINARY);
	}

	static public ColumnDefinition DATE(String name) {
		return new ColumnDefinition(name, DATE);
	}

	public String getAutoIncrment() {
		return autoIncrment;
	}

	static public ColumnDefinition TIME(String name) {
		return new ColumnDefinition(name, TIME);
	}

	static public ColumnDefinition TIMESTAMP(String name) {
		return new ColumnDefinition(name, TIMESTAMP);
	}

	static Pattern CONST_PATTERN = Pattern.compile(
			"([\\w\\d]+)" + " (\\w+)(?:\\((\\d*)(?:,(\\d*))?\\))?" + "(?: (PRIMARY KEY))?" + "(?: (AUTO_INCREMENT))?"
					+ "(?: ((?:NOT )?NULL))?" + "(?: DEFAULT \\'([^\\']+(?:(?:\\'\\')[^\\']*)*)\\')?"
					+ "(?: REMARKS \\'([^\\']+(?:(?:\\'\\')[^\\']*)*)\\')?");

	public static ColumnDefinition valueOf(String sql) {
		Matcher matcher = CONST_PATTERN.matcher(sql);
		ColumnDefinition column = null;
		if (matcher.find()) {
			int j = 1;
			String name = matcher.group(j++);
			String type = matcher.group(j++);

			column = Column(JDBCType.valueOf(type), name);

			String size = matcher.group(j++);
			if (size != null) {
				column.size(Integer.parseInt(size));
			}
			String digit = matcher.group(j++);
			if (digit != null) {
				column.digits(Integer.parseInt(digit));
			}
			String primaryKey = matcher.group(j++);
			if (primaryKey != null && "PRIMARY KEY".equals(primaryKey)) {
				column.primarykey();
			}
			String autoIncrement = matcher.group(j++);
			if (autoIncrement != null && "AUTO_INCREMENT".equals(autoIncrement)) {
				column.autoIncrement();
			}
			String nullable = matcher.group(j++);
			if (nullable != null) {
				if ("NOT NULL".endsWith(nullable)) {
					column.required();
				} else if ("NULL".endsWith(nullable)) {
					column.required(false);
				}
			}
			String defaultValue = matcher.group(j++);
			if (defaultValue != null) {
				column.defaultValue(defaultValue.replaceAll("\'\'", "\'"));
			}
			String remark = matcher.group(j++);
			if (remark != null) {
				column.remarks(remark.replaceAll("\'\'", "\'"));
			}
		}
		return column;
	}

	public String getColumnName() {
		return columnName;
	}

	public JDBCType getDataType() {
		return dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public int getNullable() {
		return primarykey ? 0 : nullable;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public int getCharOctetLength() {
		return charOctetLength;
	}

	public int getOrdinalPosition() {
		return ordinalPosition;
	}

	public short getSourceDataType() {
		return sourceDataType;
	}

	public boolean isUnsigned() {
		return unsigned;
	}

	public boolean isPrimarykey() {
		return primarykey;
	}
}