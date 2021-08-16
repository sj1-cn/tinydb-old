/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.queries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;

class Columns implements HasSQLRepresentation {
    private static final String ALL = "*";
    private List<String> columns;

    private Columns() {
        columns = new ArrayList<>();
    }

    Columns(Columns columns) {
        this.columns = new ArrayList<>(columns.columns);
    }

    public static Columns empty() {
        return new Columns();
    }

    public static Columns all() {
        return new Columns().add(ALL);
    }

    public Columns add(String... columns) {
        Collections.addAll(this.columns, columns);
        return this;
    }

    public void count() {
        clear().add(String.format("COUNT(%s)", ALL));
    }

    public void countDistinct(String column) {
        clear().add(String.format("COUNT(DISTINCT %s)", column));
    }

    int size() {
        return columns.size();
    }

    Columns clear() {
        columns.clear();
        return this;
    }

    @Override
    public String toDemoSQL() {
        if (columns.isEmpty()) columns.add(ALL);

        return String.join(", ", columns.toArray(new String[]{}));
    }
}
