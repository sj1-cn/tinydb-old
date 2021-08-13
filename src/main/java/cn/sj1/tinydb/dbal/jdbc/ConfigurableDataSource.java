/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package cn.sj1.tinydb.dbal.jdbc;

import java.util.Properties;

import javax.sql.DataSource;

public class ConfigurableDataSource {
	public static DataSource connectAsUserWith(Properties credentials) {
//    	DataSource source = new DataSource();
//        source.setUser(credentials.getProperty("user"));
//        source.setPassword(credentials.getProperty("password"));

		return null;
	}

	public static DataSource connectViaUrlWith(Properties credentials) {
//        MysqlDataSource source = new MysqlDataSource();
//        source.setURL(credentials.getProperty("url"));
//        source.setUser(credentials.getProperty("user"));
//        source.setPassword(credentials.getProperty("password"));

		return null;
	}
}
