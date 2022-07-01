package hello.jdbc.repository.ex;


//런타임 예외를 상속받았으니 이클래스는 언체크 예외
public class MyDbException extends RuntimeException{
    public MyDbException() {
    }

    public MyDbException(String message) {
        super(message);
    }

    public MyDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDbException(Throwable cause) {
        super(cause);
    }
}
