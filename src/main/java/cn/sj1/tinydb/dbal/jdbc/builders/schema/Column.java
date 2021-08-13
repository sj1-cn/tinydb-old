/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc.builders.schema;

import cn.sj1.tinydb.dbal.jdbc.builders.HasSQLRepresentation;

public interface Column extends HasSQLRepresentation {
	String getName();
}
