/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;
import cn.sj1.tinydb.dbal.jdbc.statements.InsertStatement;
import cn.sj1.tinydb.dbal.jdbc.statements.SQLError;
import cn.sj1.tinydb.dbal.jdbc.statements.SelectStatement;
import cn.sj1.tinydb.dbal.jdbc.statements.UpdateStatement;

abstract public class Table<T> {
    private final Connection connection;

    public Table(Connection connection) {
        this.connection = connection;
    }

    protected InsertStatement<T> createInsert(String... columns){
        return new InsertStatement<>(connection, table(), mapper()).columns(columns);
    }

    protected UpdateStatement createUpdate(String... columns) {
        return new UpdateStatement(connection, table()).columns(columns);
    }

    protected SelectStatement<T> select(String... columns) {
        return new SelectStatement<>(connection, table(), mapper()).select(columns);
    }

    protected void executeUpdate(
        HasSQLRepresentation insertOrUpdate,
        Object... parameters
    ) {
        try (PreparedStatement statement = connection.prepareStatement(
            insertOrUpdate.toDemoSQL()
        )) {
            QueryParameters.bind(statement, parameters);
            statement.execute();
        } catch (SQLException e) {
            throw SQLError.producedBy(insertOrUpdate, parameters, e);
        }
    }

    abstract protected String table();

    abstract protected RowMapper<T> mapper();
}
