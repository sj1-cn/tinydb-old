/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.statements;

import java.sql.SQLException;
import java.util.Arrays;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;

public class SQLError extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SQLError(String format, SQLException cause) {
        super(format, cause);
    }

    public static SQLError producedBy(
        HasSQLRepresentation statement,
        Object[] parameters,
        SQLException cause
    ) {
        return new SQLError(
            String.format(
                "Cannot execute statement %s with parameters %s",
                statement.toSQL(),
                Arrays.toString(parameters)
            ),
            cause
        );
    }
}
