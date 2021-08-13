package nebula.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TestBase {

	String testname;

	@BeforeEach
	public void setUp(TestInfo testinfo) {
		this.testname = testinfo.getTestMethod().get().getName();
	}

	public Connection openConnection() {
		return openConnection(this.getClass().getName() + "_" + testname);
	}

	static public void assertListtoString(List<?> expected, List<?> actual) {
		assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).toString(), actual.get(i).toString());
		}
	}

	public Connection openConnection(String name) {
		try {
			HikariConfig config = new HikariConfig();
			config.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource");
			config.setConnectionTestQuery("VALUES 1");
			config.addDataSourceProperty("URL", // jdbc:h2:mem:test
					"jdbc:h2:mem:" + name + ";DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MSSQLServer");
			config.addDataSourceProperty("user", "sa");
			config.addDataSourceProperty("password", "sa");

			dataSource = new HikariDataSource(config);
			connection = dataSource.getConnection();
			return connection;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	HikariDataSource dataSource;
	Connection connection;

	public void closeConnection() {
		try {
			connection.close();
			dataSource.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}