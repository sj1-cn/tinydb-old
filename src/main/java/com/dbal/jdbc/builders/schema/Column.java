/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.dbal.jdbc.builders.schema;

import com.dbal.jdbc.builders.HasSQLRepresentation;

public interface Column extends HasSQLRepresentation {
	String getName();
}
