package cn.sj1.tinydb.jdbc.builders.schema.ddl;

import cn.sj1.tinydb.jdbc.builders.schema.ColumnDefinition;

public class AlterTable {
	public static class AddColumnCommand extends AlterTableColumnCommand {

		public AddColumnCommand(ColumnDefinition column) {
			super(column);
		}

	}

	public static class ChangeColumnTypeCommand extends AlterTableColumnCommand {

		public ChangeColumnTypeCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class DropColumnCommand extends AlterTableColumnCommand {

		public DropColumnCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class AlterColumnNullableCommand extends AlterTableColumnCommand {

		public AlterColumnNullableCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class AlterColumnRemarksCommand extends AlterTableColumnCommand {

		public AlterColumnRemarksCommand(ColumnDefinition column) {
			super(column);
		}
	}

	public static class RenameColumnCommand extends AlterTableColumnCommand {

		ColumnDefinition oldColumn;

		public RenameColumnCommand(ColumnDefinition oldColumn, ColumnDefinition newColumn) {
			super(newColumn);
			this.oldColumn = oldColumn;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(this.getClass().getSimpleName());
			builder.append(" [");
			builder.append(oldColumn);
			builder.append("]");
			builder.append(">");
			builder.append("[");
			builder.append(column);
			builder.append("]");
			return builder.toString();
		}
	}
}
