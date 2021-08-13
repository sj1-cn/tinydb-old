package cn.sj1.nebula.jdbc.builders.schema;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cn.sj1.nebula.jdbc.builders.schema.JDBC.ColumnType;

public class JDBCConfigurationTest {

	@Test
	public void testColumnDefault() {
		assertEquals("ColumnDefault [name=VARCHAR, size=10, digit=0]", ColumnType.valueOf("VARCHAR(10)").toString());
		assertEquals("ColumnDefault [name=VARCHAR, size=0, digit=0]", ColumnType.valueOf("VARCHAR").toString());
		assertEquals("ColumnDefault [name=VARCHAR, size=10, digit=5]",
				ColumnType.valueOf("VARCHAR(10,5)").toString());
	}

}
