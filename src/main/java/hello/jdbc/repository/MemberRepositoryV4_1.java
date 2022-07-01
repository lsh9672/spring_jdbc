package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.ex.MyDbException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * 예외 누수 문제 해결
 * 체크 예외를 런타임 예외로 변경
 * MemberRepository 인터페이스 사용
 * throws SQLExcetion 제거
 */

@Slf4j
public class MemberRepositoryV4_1 implements MemberRepository{

    private final DataSource dataSource;

    public MemberRepositoryV4_1(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member){
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
            throw new MyDbException(e);
        }
        finally {

            //시작과 역순으로 close해줘야됨
//            pstmt.close(); //Exception이 터지면 아래의 커넥션 close가 호출이 안될 수도 있음
//            con.close();

            close(con,pstmt,null);
        }
    }

    @Override
    public Member findById(String memberId) {
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
            throw new MyDbException(e);
        }
        finally {
            close(con,pstmt,rs);
        }
    }

    @Override
    public void update(String memberId, int money){
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
            throw new MyDbException(e);
        }
        finally {

            close(con,pstmt,null);
        }
    }

    @Override
    public void delete(String memberId){
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
            throw new MyDbException(e);
        }
        finally {

            close(con,pstmt,null);
        }

    }


    private void close(Connection con, Statement stmt, ResultSet rs){

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        //주의!, 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        DataSourceUtils.releaseConnection(con,dataSource);

    }

    private Connection getConnection() throws SQLException {
        //주의! 트랜잭션 동기화를 사용하려면 DataSourceUtils를 사용해야 한다.
        //커넥션이 필요하면 트랜잭션 동기화 매니저에 보관된 커넥션을 꺼내 사용한다.
        Connection con = DataSourceUtils.getConnection(dataSource);

        log.info("get connection = {}, class={}",con,con.getClass());
        return con;
    }
}
