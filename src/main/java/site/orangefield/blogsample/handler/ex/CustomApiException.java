package site.orangefield.blogsample.handler.ex;

public class CustomApiException extends RuntimeException {

    public CustomApiException(String message) {
        super(message); // RuntimeException 에게 던지기
    }
}
