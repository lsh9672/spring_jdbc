# spring_jdbc
인프런 스프링 DB 1편 - 데이터 접근 핵심원리

## 일지

### (섹션1 - 2022.06.02)

- jdbc 기본개념 및 간단한 예제 코드 작성

- jdbc를 이용해서 crud를 구현하고 테스트 코드 작성

- `commit` : [d3c1bdb](https://github.com/lsh9672/spring_jdbc/commit/d3c1bdbd3fe92a9cc2a8514ca8941324ffef3fcf)

- `내용정리` : [JDBC 이해](https://www.notion.so/1-JDBC-d9045f6e40c6429bb9cdf903717cbed4)

### (섹션2 - 2022.06.03)

- 커넥션을 하는 방법에 대한 이해

- 커넥션 풀과 Data source를 이해하고 예제 코드 작성

- 커넥션 풀은 스프링 부트가 기본으로 사용하는 HikariCP를 이용함

- `commit` : [fbe452b](https://github.com/lsh9672/spring_jdbc/commit/fbe452bd2c136d0b380fb62fa26697b2060af51d)

- `내용정리` : [커넥션 풀과 데이터소스 이해](https://www.notion.so/2-656a84c437d64cc8b0f203f3862bfa6c)

### (섹션3 - 2022.06.06 ~ 2022.06.07)

- 트랜잭션과 DB락에 대한 기본개념 이해

- h2의 콘솔에서 autocommit을 해제후, 트랜잭션과 DB락 테스트를 해봄

- JDBC를 이용해서 간단한 계좌이체 상황을 만들고, 중간에 예외상황 발생으로 인해 발생하는 문제점을 트랜잭션 롤백을 통해 해결하는 코드를 작성

- `commit` : [9f03dd9](https://github.com/lsh9672/spring_jdbc/commit/9f03dd90f61d6c58ada412af983ad853606f8ab0)

- `내용정리` : [트랜잭션 이해](https://www.notion.so/3-0ffc839c0fbd4b00845fa0db108988da)

### (섹션4 - 2022.06.09)

- 트랜잭션 적용을 위한 코드의 문제점을 확인하고 이를 해결

- 트랜잭션 매니저 -> 트랜잭션 템플릿 -> 트랜잭션 AOP 순으로 적용해보면서 코드를 개선함

- `commit` : [339452e](https://github.com/lsh9672/spring_jdbc/commit/339452e7bd74ee7bfeddc83e2aac78bc987ea7ef)

- `내용정리` : [스프링과 문제 해결 - 트랜잭션](https://www.notion.so/4-4e958df5231440d2bb922e46a49ac8e8)

### (섹션5 - 2022.06.10)

- 자바에서 사용하는 예외처리의 개념이해

- checked exception 과 unchecked exception에 대해 이해

- checked exception 사용시 발생하는 의존문제를 unchecked exception을 통해 해결

- `commit` : [b7c93e9](https://github.com/lsh9672/spring_jdbc/commit/b7c93e959d54f89c463add8fb61664c290c6ac4a)

- `내용정리` : [자바 예외 이해](https://www.notion.so/5-c52ad409fece43a3ae143d6927b73cb8)

### (섹션6 - 2022.07.01)

- 스프링을 이용해서 예외들을 처리함.

- 스프링이 제공하는 템플릿 콜백패턴을 이용해서 중복을 제거함.

- `commit` : [6be16ed](https://github.com/lsh9672/spring_jdbc/commit/6be16eda4f927c604adda15b3834e51cfc0fcbaf)

- `내용정리` : [스프링과 문제해결](https://www.notion.so/6-5ba9f329f88b436c87f805f63fe1c107)
