package site.orangefield.blogsample.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import site.orangefield.blogsample.handler.ex.CustomApiException;
import site.orangefield.blogsample.handler.ex.CustomException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(Exception e) { // fetch 요청시 발동
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String htmlException(Exception e) { // 일반적인 요청 Get(a태그), Post(form태그) 요청

        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + e.getMessage() + "');");
        sb.append("history.back();"); // 쓴 내용이 날아가지 않도록 뒤로가기
        sb.append("</script>");
        return sb.toString();
    }
}
