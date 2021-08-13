package cn.sj1.nebula.data.query;

import static cn.sj1.nebula.data.query.Condition.and;
import static cn.sj1.nebula.data.query.Condition.field;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SqlTest {

	@Test
	public void testOrderBy() {
		assertEquals("ORDER BY name ASC", OrderBy.empty().orderBy("name").toString());
		assertEquals("ORDER BY name DESC", OrderBy.empty().orderBy("name").desc().toString());
		assertEquals("ORDER BY name ASC, age DESC", OrderBy.empty().orderBy("name").orderBy("age").desc().toString());
	}

	@Test
	public void testCondition() {
		assertEquals("name = \'testname\'", toString(field("name").eq("testname")));
		assertEquals("name <> \'testname\'", toString(field("name").ne("testname")));
		assertEquals("name LIKE 'testname%'", toString(field("name").startWith("testname")));
		assertEquals("name LIKE '%testname'", toString(field("name").endWith("testname")));
		assertEquals("name LIKE '%testname%'", toString(field("name").contain("testname")));
		assertEquals("name LIKE 'testname'", toString(field("name").like("testname")));
		assertEquals("age = 10", toString(field("age").eq(10)));
		assertEquals("age <> 10", toString(field("age").ne(10)));
		assertEquals("age > 10", toString(field("age").gt(10)));
		assertEquals("age >= 10", toString(field("age").ge(10)));
		assertEquals("age < 10", toString(field("age").lt(10)));
		assertEquals("age <= 10", toString(field("age").le(10)));
		assertEquals("age IN (\'10\', \'20\', \'30\', \'40\')", toString(field("age").in("10", "20", "30", "40")));
		assertEquals("age IN (10, 20, 30, 40)", toString(field("age").in(10, 20, 30, 40)));
		assertEquals("age BETWEEN 10 AND 20", toString(field("age").between(10, 20)));
		assertEquals("name = \'testname\' AND id = 10", toString(field("name").eq("testname").and("id").eq(10)));
		assertEquals("(name = \'testname\' AND id = 10) AND age > 50",
				toString(field("name").eq("testname").and("id").eq(10).and("age").gt(50)));
		assertEquals("name = \'testname\' AND (id = 10 AND age > 50)",
				toString(field("name").eq("testname").and(field("id").eq(10).and("age").gt(50))));
		assertEquals("name = \'testname\' AND id >= 10", toString(and(field("name").eq("testname"), field("id").ge(10))));
	}

	private String toString(Condition c) {
		CommonSQLConditionVisitor conditionVisitorImpl = new CommonSQLConditionVisitor();
		c.accept(conditionVisitorImpl);
		return conditionVisitorImpl.toString();
	}
}
