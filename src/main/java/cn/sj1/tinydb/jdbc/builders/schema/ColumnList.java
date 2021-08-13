package cn.sj1.tinydb.jdbc.builders.schema;

import java.util.List;
import java.util.stream.Collectors;

import cn.sj1.tinydb.commons.list.StringListMap;
import cn.sj1.tinydb.dbal.jdbc.builders.schema.Column;

public class ColumnList extends StringListMap<ColumnDefinition> {
	public ColumnList() {
		super(c -> c.columnName);
	}

	public void addColumn(String strColumnDefinition) {
		this.push(ColumnDefinition.valueOf(strColumnDefinition));
	}

	public void addColumn(ColumnDefinition columnDefinition) {
		this.push(columnDefinition);
	}

	public ColumnList copy() {
		ColumnList newobject = new ColumnList();
		for (ColumnDefinition columnDefinition : super.list()) {
			newobject.push(columnDefinition);
		}
		return newobject;
	}

	public List<ColumnDefinition> all() {
		return list();
	}

	public List<ColumnDefinition> primaryKeys() {
		return this.list().stream().filter(c -> c.primarykey).collect(Collectors.toList());
	}

	public List<ColumnDefinition> others() {
		return this.list().stream().filter(c -> !c.primarykey).collect(Collectors.toList());
	}

	public String[] names() {
		String[] names = new String[this.size()];
		for (int i = 0; i < this.size(); i++) {
			names[i] = this.get(i).getName();
		}
		return names;
	}

	public static String[] namesOf(ColumnList columns) {
		String[] names = new String[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			names[i] = columns.get(i).getName();
		}
		return names;
	}

	public static <T extends Column> String[] namesOf(List<T> columns) {
		String[] names = new String[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			names[i] = columns.get(i).getName();
		}
		return names;
	}

}
