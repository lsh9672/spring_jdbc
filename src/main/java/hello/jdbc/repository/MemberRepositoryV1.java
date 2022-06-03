package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource 사용, JDBCUtils 사용
 */

@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        /*
        아래와 같이 파라미터 바인딩을 하면 ?부분은 단순한 데이터 취급을 하기 떄문에 , 해당 부분에 쿼리문같은 로직이 들어와서 발생할수 있는
        sql injection문제를 예방할수 있다.
         */

        String sql = "insert into member(member_id,money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2,member.getMoney());
            //영향받은 row수를 반환함 - 업데이트를 날렸는데 10개의 로우가 영향을 받았으면 10이 반환됨.
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {

            //시작과 역순으로 close해줘야됨
//            pstmt.close(); //Exception이 터지면 아래의 커넥션 close가 호출이 안될 수도 있음
//            con.close();

            close(con,pstmt,null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);

            //rs는 select 쿼리의 결과를 가지고 있는 일종의 통
            rs = pstmt.executeQuery();
            if(rs.next()){
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            }
            else{
                throw new NoSuchElementException("member not found memberId = " + memberId);
            }
        }
        catch (SQLException e){
            log.error("db error",e);
            throw e;
        }
        finally {
            close(con,pstmt,rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);

            pstmt.setString(2,memberId);
            //영향받은 row수를 반환함 - 업데이트를 날렸는데 10개의 로우가 영향을 받았으면 10이 반환됨.
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {

            close(con,pstmt,null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1,memberId);

            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        }
        finally {

            close(con,pstmt,null);
        }

    }


    private void close(Connection con, Statement stmt, ResultSet rs){

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);

    }

    private Connection getConnection() throws SQLException {
        Connection con = dataSource.getConnection();
        log.info("get connection = {}, class={}",con,con.getClass());
        return con;
    }
}
