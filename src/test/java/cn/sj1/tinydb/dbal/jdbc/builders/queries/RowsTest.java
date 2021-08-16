/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RowsTest {
    private Rows rows;

    @BeforeEach
    public void initRows() {
        rows = Rows.all();
    }

    @Test
    public void it_converts_to_sql_an_object_with_no_offset_and_limit() {
        assertEquals("", rows.toDemoSQL());
    }

    @Test
    public void it_converts_to_sql_an_object_with_5_rows_as_limit() {
        rows.countTo(5);

        assertEquals("LIMIT 5", rows.toDemoSQL());
    }

    @Test
    public void it_converts_sql_an_object_with_10_rows_and_offset_of_30() {
        rows.countTo(10);
        rows.startingAt(30);

        assertEquals("LIMIT 10 OFFSET 30", rows.toDemoSQL());
    }

    @Test
    public void it_does_not_allow_a_negative_limit() {
    	assertThrows(IllegalArgumentException.class, () ->{ rows.countTo(-3);});
    }

    @Test
    public void it_does_not_allow_a_negative_offset() {
    	assertThrows(IllegalArgumentException.class, () ->{  rows.startingAt(-4);});
    }
}
