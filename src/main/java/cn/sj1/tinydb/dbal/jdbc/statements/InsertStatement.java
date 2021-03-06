/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.statements;

import cn.sj1.tinydb.dbal.jdbc.QueryParameters;
import cn.sj1.tinydb.dbal.jdbc.RowMapper;
import cn.sj1.tinydb.dbal.jdbc.builders.queries.Insert;

import java.sql.*;

public class InsertStatement<T> extends SQLStatement {
    private Insert insert;
    private final RowMapper<T> mapper;

    public InsertStatement(
        Connection connection,
        String table,
        RowMapper<T> mapper
    ) {
        super(connection);
        this.insert = Insert.into(table);
        this.mapper = mapper;
    }

    public InsertStatement<T> columns(String... columns) {
        insert.columns(columns);
        return this;
    }

    public Hydrator<T> execute(Object... parameters) {
        try (PreparedStatement statement = connection.prepareStatement(
            insert.toDemoSQL(),
            Statement.RETURN_GENERATED_KEYS
        )) {
            QueryParameters.bind(statement, parameters);
            statement.executeUpdate();
            ResultSet key = statement.getGeneratedKeys();
            key.next();
            return new Hydrator<>(key.getLong(1), parameters, mapper);
        } catch (SQLException e) {
            throw SQLError.producedBy(insert, parameters, e);
        }
    }
}
