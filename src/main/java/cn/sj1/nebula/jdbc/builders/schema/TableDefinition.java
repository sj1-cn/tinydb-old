package cn.sj1.nebula.jdbc.builders.schema;

public class TableDefinition {
	String tableName;
	ColumnList columnList;

	public TableDefinition(String tableName, ColumnList columnList) {
		super();
		this.tableName = tableName;
		this.columnList = columnList;
	}

	public String getTableName() {
		return tableName;
	}

	public ColumnList getColumnList() {
		return columnList;
	}

	public TableDefinition(String tableName) {
		super();
		this.tableName = tableName;
		columnList = new ColumnList();
	}

}
