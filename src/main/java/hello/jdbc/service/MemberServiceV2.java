package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import hello.jdbc.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection();
        try{
            //오토커밋 false를 하는 sql을 디비에 날려줌
            con.setAutoCommit(false);

            //비즈니스 로직
            bizLogic(con,fromId, toId, money);

            //커밋 또는 롤백
            con.commit(); //성공시 커밋
        }
        catch(Exception e){
            con.rollback(); //실패시 롤백
            throw new IllegalStateException(e);
        }
        finally{
            release(con);
        }

    }

    private void bizLogic(Connection con,String fromId, String toId, int money) throws SQLException {
        //트랜잭션 시작
        //멤버 조회
        Member fromMember = memberRepository.findById(con, fromId);

        //누구에게 보낼지, 보낼상대의 정보 조회
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        //오류케이스만들기
        validation(toMember);

        memberRepository.update(con, toId,toMember.getMoney() + money);
    }

    private void release(Connection con) {
        if(con != null){
            try{
                //위에서 해당 커넥션의 오토커밋모드를 false로 꺼버렸다.
                //이상태에서 커넥션 풀로 반환이 되면, 해당 커넥션은 오토커밋이 꺼져있는 상태이므로, 의도치 않게 문제가 발생할수 있다.
                //일반적으로 오토커밋 모드로 사용하기 떄문에 문제가 발생할수 있어서, 오토커밋모드를 true로 만들고 반환해야 된다.
                con.setAutoCommit(true); //커넥션 풀 고려
                con.close();
            }
            catch(Exception e){
                log.info("error",e);
            }
            finally{

            }
        }
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 예외발생");
        }
    }
}
