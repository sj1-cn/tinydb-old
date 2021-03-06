/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.schema;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;

public class ForeignKey implements HasSQLRepresentation {
    private final Column column;
    private String foreignKeyName;
    private String table;

    ForeignKey(Column column) {
        this.column = column;
    }

    public ForeignKey references(String name) {
        this.foreignKeyName = name;
        return this;
    }

    public ForeignKey on(String table) {
        this.table = table;
        return this;
    }

    @Override
    public String toDemoSQL() {
        assertReferenceIsSet();
        return String.format(
            "FOREIGN KEY (%s) REFERENCES %s(%s)",
            column.getName(),
            table,
            foreignKeyName
        );
    }

    private void assertReferenceIsSet() {
        if (table == null) {
            throw new IllegalStateException(
                "Cannot determine to which table this key refers to"
            );
        }
        if (foreignKeyName == null) {
            throw new IllegalStateException(
                "Cannot determine to which column this key refers to"
            );
        }
    }
}
