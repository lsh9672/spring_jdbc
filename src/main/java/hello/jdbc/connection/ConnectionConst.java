package hello.jdbc.connection;

public abstract class ConnectionConst {

    //h2 디비는 다음과 같이 접근함(규약이므로 지켜야됨)
    public static final String URL = "jdbc:h2:tcp://localhost/~/lsh/h2_db_collection/springJDBC/springJDBC";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
