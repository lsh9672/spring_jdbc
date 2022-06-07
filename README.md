# spring_jdbc
인프런 스프링 DB 1편 - 데이터 접근 핵심원리

## 일지

### (섹션1)

- jdbc 기본개념 및 간단한 예제 코드 작성

- jdbc를 이용해서 crud를 구현하고 테스트 코드 작성

- `commit` : [d3c1bdb](https://github.com/lsh9672/spring_jdbc/commit/d3c1bdbd3fe92a9cc2a8514ca8941324ffef3fcf)

### (섹션2)

- 커넥션을 하는 방법에 대한 이해

- 커넥션 풀과 Data source를 이해하고 예제 코드 작성

- 커넥션 풀은 스프링 부트가 기본으로 사용하는 HikariCP를 이용함

- `commit` : [fbe452b](https://github.com/lsh9672/spring_jdbc/commit/fbe452bd2c136d0b380fb62fa26697b2060af51d)

### (섹션3)

- 트랜잭션과 DB락에 대한 기본개념 이해

- h2의 콘솔에서 autocommit을 해제후, 트랜잭션과 DB락 테스트를 해봄

- JDBC를 이용해서 간단한 계좌이체 상황을 만들고, 중간에 예외상황 발생으로 인해 발생하는 문제점을 트랜잭션 롤백을 통해 해결하는 코드를 작성

- `commit` : [9f03dd9](https://github.com/lsh9672/spring_jdbc/commit/9f03dd90f61d6c58ada412af983ad853606f8ab0)
