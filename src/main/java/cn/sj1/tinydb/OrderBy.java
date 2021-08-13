package cn.sj1.tinydb;

import java.util.ArrayList;
import java.util.List;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;

public class OrderBy implements HasSQLRepresentation {
	List<OrderByItem> orderbys = new ArrayList<>();

	OrderByItem lastItem;

	static public OrderBy empty() {
		return new OrderBy();
	}

	public OrderBy() {
	}

	public OrderBy asc() {
		lastItem.op = OrderByOp.ASC;
		return this;
	}

	public OrderBy desc() {
		lastItem.op = OrderByOp.DESC;
		return this;
	}

	public OrderBy orderBy(String name) {
		lastItem = new OrderByItem(name, OrderByOp.ASC);
		orderbys.add(lastItem);
		return this;
	}

	public OrderBy andOrderBy(String name, OrderByOp op) {
		lastItem = new OrderByItem(name, op);
		orderbys.add(lastItem);
		return this;
	}

	@Override
	public String toSQL() {
		if (orderbys.size() > 0) {
			List<String> str = new ArrayList<>();
			for (OrderByItem o : orderbys) {
				str.add(String.format(o.op.format, o.field));
			}
			return "ORDER BY " + String.join(", ", str);
		} else {
			return "";
		}
	}

	@Override
	public String toString() {
		return toSQL();
	}

}
