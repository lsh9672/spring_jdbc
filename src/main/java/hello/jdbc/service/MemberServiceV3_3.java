package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * 트랜잭션 - @Transactional AOP
 */
@Slf4j
public class MemberServiceV3_3 {


    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_3(MemberRepositoryV3 memberRepository) {
        this.memberRepository = memberRepository;
    }

    //해당 메서드를 실행할때 트랜잭션을 시작하고 성공하면 커밋, 실패하면 롤백하겠다.
    @Transactional
    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //비즈니스 로직
        bizLogic(fromId, toId, money);

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
