/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cn.sj1.tinydb.dbal.jdbc.builders.queries.WhereExpression.Operator;

public class WhereExpressionTest {
    @Test
    public void it_converts_to_sql_an_and_expression() {
        WhereExpression where = WhereExpression.with(
            "u.username = ?",
            Operator.AND
        );
        assertEquals("AND u.username = ?", where.toDemoSQL());
    }

    @Test
    public void it_converts_to_sql_an_or_expression() {
        WhereExpression where = WhereExpression.with(
            "u.password = ?",
            Operator.OR
        );
        assertEquals("OR u.password = ?", where.toDemoSQL());
    }

    @Test
    public void it_ignores_boolean_operator_from_first_expression() {
        WhereExpression where = WhereExpression.with("u.name LIKE ?", null);
        assertEquals("u.name LIKE ?", where.toDemoSQL());
    }
}
