package cn.sj1.nebula.jdbc.builders.schema;

import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.BIT;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.DECIMAL;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.IDENTITY;
import static cn.sj1.nebula.jdbc.builders.schema.ColumnDefinition.VARCHAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ColumnDefinitionTest {

	@Test
	public void testValueOf() {
		{
			String expected = IDENTITY("id").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = BIT("female").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = IDENTITY("id").autoIncrement().toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = VARCHAR("name").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = DECIMAL("height").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}

		{
			String expected = DECIMAL("height").size(10).toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}

		{
			String expected = DECIMAL("height").size(10).digits(10).toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = DECIMAL("height").size(10).digits(10).remarks("height's remarks").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}
		{
			String expected = DECIMAL("height").size(10).digits(10).remarks("height's remarks").defaultValue("height's defaultvalue").toString();
			assertEquals(expected, ColumnDefinition.valueOf(expected).toString());
		}

	}

}
