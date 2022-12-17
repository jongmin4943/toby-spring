import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.Driver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class DataSourceTest {

    final String username = "root";
    final String password = "root";
    final String url = "jdbc:mariadb://localhost/tobi";
    final Class<Driver> driverClass = Driver.class;

    @DisplayName("데이터베이스 컨넥션 테스트")
    @Test
    public void databaseConnectionTest() throws SQLException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);

        final Connection connection = dataSource.getConnection();
        final boolean isClosed = connection.isClosed();
        assertFalse(isClosed);
    }

}