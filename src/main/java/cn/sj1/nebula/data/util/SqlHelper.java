package cn.sj1.nebula.data.util;

import com.dbal.jdbc.builders.queries.Select;

public class SqlHelper {

	public Select select(String string) {
		return Select.columns(string);
	}

}
