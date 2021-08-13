package cn.sj1.tinydb.data.util;

import cn.sj1.tinydb.dbal.jdbc.builders.queries.Select;

public class SqlHelper {

	public Select select(String string) {
		return Select.columns(string);
	}

}
