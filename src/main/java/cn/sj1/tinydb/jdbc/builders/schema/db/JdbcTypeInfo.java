package cn.sj1.tinydb.jdbc.builders.schema.db;

public class JdbcTypeInfo {
	String typeName;
	int dataType;
	int precition;
	String literalProfix;
	String literalSuffix;
	String createParams;
	int nullable;
	boolean caseSensitive;
	int searchable;
	short unsignnedAttribute;
	String fixedPrecScale;

	String autoincrment;
	String localTypeName;
	String minimumScale;
	String maxmumScale;
	String numPrecRadix;

	public JdbcTypeInfo(String typeName, int dataType, int precition, String literalProfix, String literalSuffix,
			String createParams, int nullable, boolean caseSensitive, int searchable, short unsignnedAttribute,
			String fixedPrecScale, String autoincrment, String localTypeName, String minimumScale, String maxmumScale,
			String numPrecRadix) {
		super();
		this.typeName = typeName;
		this.dataType = dataType;
		this.precition = precition;
		this.literalProfix = literalProfix;
		this.literalSuffix = literalSuffix;
		this.createParams = createParams;
		this.nullable = nullable;
		this.caseSensitive = caseSensitive;
		this.searchable = searchable;
		this.unsignnedAttribute = unsignnedAttribute;
		this.fixedPrecScale = fixedPrecScale;
		this.autoincrment = autoincrment;
		this.localTypeName = localTypeName;
		this.minimumScale = minimumScale;
		this.maxmumScale = maxmumScale;
		this.numPrecRadix = numPrecRadix;
	}

	public String getTypeName() {
		return typeName;
	}

	public int getDataType() {
		return dataType;
	}

	public int getPrecition() {
		return precition;
	}

	public String getLiteralProfix() {
		return literalProfix;
	}

	public String getLiteralSuffix() {
		return literalSuffix;
	}

	public String getCreateParams() {
		return createParams;
	}

	public int getNullable() {
		return nullable;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public int getSearchable() {
		return searchable;
	}

	public short getUnsignnedAttribute() {
		return unsignnedAttribute;
	}

	public String getFixedPrecScale() {
		return fixedPrecScale;
	}

	public String getAutoincrment() {
		return autoincrment;
	}

	public String getLocalTypeName() {
		return localTypeName;
	}

	public String getMinimumScale() {
		return minimumScale;
	}

	public String getMaxmumScale() {
		return maxmumScale;
	}

	public String getNumPrecRadix() {
		return numPrecRadix;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JdbcTypeInfo [typeName=")
			.append(typeName)
			.append(", dataType=")
			.append(dataType)
			.append(", precition=")
			.append(precition)
			.append(", literalProfix=")
			.append(literalProfix)
			.append(", literalSuffix=")
			.append(literalSuffix)
			.append(", createParams=")
			.append(createParams)
			.append(", nullable=")
			.append(nullable)
			.append(", caseSensitive=")
			.append(caseSensitive)
			.append(", searchable=")
			.append(searchable)
			.append(", unsignnedAttribute=")
			.append(unsignnedAttribute)
			.append(", fixedPrecScale=")
			.append(fixedPrecScale)
			.append(", autoincrment=")
			.append(autoincrment)
			.append(", localTypeName=")
			.append(localTypeName)
			.append(", minimumScale=")
			.append(minimumScale)
			.append(", maxmumScale=")
			.append(maxmumScale)
			.append(", numPrecRadix=")
			.append(numPrecRadix)
			.append("]\n");
		return builder.toString();
	}

}
