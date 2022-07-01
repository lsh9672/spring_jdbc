package hello.jdbc.repository;

import hello.jdbc.domain.Member;

import java.sql.SQLException;

//인터페이스의 메서드에 예외가 들어가면, 구현기술이 바꼈을때 다 바꿔야 됨
public interface MemberRepositoryEx {

    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
