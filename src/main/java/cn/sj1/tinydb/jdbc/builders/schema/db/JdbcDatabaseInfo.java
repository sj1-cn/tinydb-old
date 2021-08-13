package cn.sj1.tinydb.jdbc.builders.schema.db;

import cn.sj1.tinydb.commons.list.ListMap;

public class JdbcDatabaseInfo {
	String databaseProductName;
	String databaseProductVersion;
	int databaseMajorVersion;
	int databaseMinorVersion;
	boolean generatedKeyAlwaysReturned;
	int maxColumnNameLength;
	ListMap<Integer, JdbcTypeInfo> types;

	public String getDatabaseProductName() {
		return databaseProductName;
	}

	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	public int getDatabaseMajorVersion() {
		return databaseMajorVersion;
	}

	public int getDatabaseMinorVersion() {
		return databaseMinorVersion;
	}

	public boolean isGeneratedKeyAlwaysReturned() {
		return generatedKeyAlwaysReturned;
	}

	public int getMaxColumnNameLength() {
		return maxColumnNameLength;
	}

	public ListMap<Integer, JdbcTypeInfo> getTypes() {
		return types;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JdbcDatabase [databaseProductName=")
			.append(databaseProductName)
			.append(", databaseProductVersion=")
			.append(databaseProductVersion)
			.append(", databaseMajorVersion=")
			.append(databaseMajorVersion)
			.append(", databaseMinorVersion=")
			.append(databaseMinorVersion)
			.append(", generatedKeyAlwaysReturned=")
			.append(generatedKeyAlwaysReturned)
			.append(", maxColumnNameLength=")
			.append(maxColumnNameLength)
			.append(", types=")
			.append(types)
			.append("]");
		return builder.toString();
	}

	public JdbcDatabaseInfo(String databaseProductName, String databaseProductVersion, int databaseMajorVersion,
			int databaseMinorVersion, boolean generatedKeyAlwaysReturned, int maxColumnNameLength,
			ListMap<Integer, JdbcTypeInfo> types) {
		super();
		this.databaseProductName = databaseProductName;
		this.databaseProductVersion = databaseProductVersion;
		this.databaseMajorVersion = databaseMajorVersion;
		this.databaseMinorVersion = databaseMinorVersion;
		this.generatedKeyAlwaysReturned = generatedKeyAlwaysReturned;
		this.maxColumnNameLength = maxColumnNameLength;
		this.types = types;
	}

}
