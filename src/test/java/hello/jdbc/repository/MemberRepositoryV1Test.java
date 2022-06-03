package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepositoryV1;

    //각 테스트가 실행되기 직전에 호출
    @BeforeEach
    void beforeEach(){
        //기본 DriverManager - 항상 새로운 커넥션을 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL,USERNAME,PASSWORD);
//


        //커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
    }

    @DisplayName("멤버 테이블 값 저장 테스트")
    @Test
    public void crudTest() throws SQLException {
        //save
        Member member = new Member("memberV10",10000);
        memberRepositoryV1.save(member);

        //findById
        Member findMember = memberRepositoryV1.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        log.info("member == findMember {}",member == findMember);
        log.info("member equals findMember {}",member.equals(findMember));
        Assertions.assertThat(findMember).isEqualTo(member);

        //update - money: 10000->20000
        memberRepositoryV1.update(member.getMemberId(), 20000);
        Member updateMember = memberRepositoryV1.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete

        memberRepositoryV1.delete(member.getMemberId());

        Assertions.assertThatThrownBy(()-> memberRepositoryV1.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}