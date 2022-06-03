package hello.jdbc.connection;


import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class ConnectionTest {

    @DisplayName("드라이버 매니저를 통한 커넥션 테스트")
    @Test
    public void driverManager() throws Exception{
        Connection con1 = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        Connection con2 = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        log.info("connection={}, class={}", con1,con1.getClass());
        log.info("connection={}, class={}", con2,con2.getClass());
    }

    @DisplayName("dataSource 드라이버 매니저를 통한 커넥션 테스트")
    @Test
    public void dataSourceDriverManager() throws Exception{
        //DriverManagerDataSource - 항상 새로운 커넥션을 획득
        //DriverManagerDataSource의 경우 DataSource 인터페이스를 구현하기 때문에, 아래와 같이 사용가능
        DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        useDataSource(dataSource);
    }

    @DisplayName("커넥션 풀을 이용한 커넥션 테스트")
    @Test
    public void dataSourceConnectionPool() throws Exception{
        //connection pooling(hikari를 사용)
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPol");

        useDataSource(dataSource);
        Thread.sleep(1000);
    }

    private void useDataSource(DataSource dataSource) throws SQLException{
        Connection con1 = dataSource.getConnection();
        Connection con2 = dataSource.getConnection();
        //풀의 갯수를 넘어서면 얼마동안 기다렸다가 에러남(시간 설정가능)
//        Connection con3 = dataSource.getConnection();
//        Connection con4 = dataSource.getConnection();
//        Connection con5 = dataSource.getConnection();
//        Connection con6 = dataSource.getConnection();
//        Connection con7 = dataSource.getConnection();
//        Connection con8 = dataSource.getConnection();
//        Connection con9 = dataSource.getConnection();
//        Connection con10 = dataSource.getConnection();
//        Connection con11 = dataSource.getConnection();

        log.info("connection={}, class={}", con1,con1.getClass());
        log.info("connection={}, class={}", con2,con2.getClass());
    }
}


