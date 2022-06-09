package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV2;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.parsing.DefaultsDefinition;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 트랜잭션 매니저
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV3_1 {

//    private final DataSource dataSource;

    //트랜잭션 매니저를 주입받음
    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랜잭션 시작
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try{

            //비즈니스 로직
            bizLogic(fromId, toId, money);

            //커밋 또는 롤백
            transactionManager.commit(status);
        }
        catch(Exception e){

            transactionManager.rollback(status); //실패시 롤백
            throw new IllegalStateException(e);
        }
        //finally를 이용해서 커넥션을 닫아줄 필요없이 transactionManager 내부에서 정리해줌

    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        //멤버 조회
        Member fromMember = memberRepository.findById(fromId);

        //누구에게 보낼지, 보낼상대의 정보 조회
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        //오류케이스만들기
        validation(toMember);

        memberRepository.update(toId,toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 예외발생");
        }
    }
}
