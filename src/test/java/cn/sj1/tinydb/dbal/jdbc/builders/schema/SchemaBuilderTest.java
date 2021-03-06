/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.schema;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

public class SchemaBuilderTest {

    @Test
    public void it_converts_to_sql_a_single_table() throws SQLException {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        SchemaBuilder builder = new SchemaBuilder(connection);
        when(connection.createStatement()).thenReturn(statement);

        Table users = builder.table("users");
        users.increments("id");
        builder.build();

        verify(statement).executeUpdate(users.toDemoSQL());
    }

    @Test
    public void it_converts_to_sql_a_several_tables() throws SQLException {
        Connection connection = mock(Connection.class);
        Statement statement = mock(Statement.class);
        SchemaBuilder builder = new SchemaBuilder(connection);
        when(connection.createStatement()).thenReturn(statement);

        Table users = builder.table("users");
        users.increments("id");
        Table roles = builder.table("roles");
        roles.increments("id");
        Table usersRoles = builder.table("users_roles");
        usersRoles.increments("id");
        builder.build();

        verify(statement).executeUpdate(users.toDemoSQL());
        verify(statement).executeUpdate(roles.toDemoSQL());
        verify(statement).executeUpdate(usersRoles.toDemoSQL());
    }
}
