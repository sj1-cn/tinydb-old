/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package nebula.jdbc.suites;

import java.sql.Connection;
import java.util.Properties;

//@RunWith(Suite.class)
//@Suite.SuiteClasses({
//    InsertStatementTest.class,
//    SelectStatementTest.class,
//    UpdateStatementTest.class
//})
public class IntegrationSuite {
    public static Connection connection;
    public static Properties credentials;
//
//    @ClassRule
//    public static ExternalResource resource = new ExternalResource() {
//        @Override
//        protected void before() throws Throwable {
//
//			HikariConfig config = new HikariConfig();
//			config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
//			config.setConnectionTestQuery("VALUES 1");
//			config.addDataSourceProperty("URL", // jdbc:h2:mem:test
//					"jdbc:h2:mem:" + "test" + ";DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
//			config.addDataSourceProperty("user", "sa");
//			config.addDataSourceProperty("password", "sa");
//
//			HikariDataSource dataSource = new HikariDataSource(config);
//			connection = dataSource.getConnection();
//        }
//
//        @Override
//        protected void after() {
//            if (connection != null) try {
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    };
}
