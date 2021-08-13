package cn.sj1.tinydb;

public class Sql {
	static public WhereBuilder where() {
		return WhereBuilder.empty();
	}

	static public ConditionBuilder<WhereBuilder> where(String name) {
		return WhereBuilder.empty().name(name);
	}
}
