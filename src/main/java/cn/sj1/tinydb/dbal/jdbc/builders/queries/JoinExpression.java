/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;

class JoinExpression implements HasSQLRepresentation {
    private final String table;
    private final String on;
    private final Type type;

    enum Type {INNER, OUTER};

    JoinExpression(String table, String on, Type type) {
        this.table = table;
        this.on = on;
        this.type = type;
    }

    public String toDemoSQL() {
        return String.format(
            "%s JOIN %s ON %s",
            type.toString(),
            table,
            on
        );
    }
}
