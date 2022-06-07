package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repository.MemberRepositoryV1;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;

@RequiredArgsConstructor
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        //트랜잭션 시작

        //멤버 조회
        Member fromMember = memberRepository.findById(fromId);

        //누구에게 보낼지, 보낼상대의 정보 조회
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        //오류케이스만들기
        validation(toMember);

        memberRepository.update(toId,toMember.getMoney() + money);

        //커밋 또는 롤백
    }

    private void validation(Member toMember) {
        if(toMember.getMemberId().equals("ex")){
            throw new IllegalStateException("이체중 예외발생");
        }
    }
}
