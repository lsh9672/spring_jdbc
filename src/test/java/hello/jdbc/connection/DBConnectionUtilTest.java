package hello.jdbc.connection;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

@Slf4j
public class DBConnectionUtilTest {

    @DisplayName("커넥션 테스트")
    @Test
    public void connectionTest() throws Exception{
        Connection connection = DBConnectionUtil.getConnection();

        Assertions.assertThat(connection).isNotNull();
    }
}
