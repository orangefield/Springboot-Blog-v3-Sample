package site.orangefield.blogsample.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import site.orangefield.blogsample.handler.ex.CustomException;

public class UtilValid {

    public static void 요청에러처리(BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>(); // 에러는 여러 개
            for (FieldError fe : bindingResult.getFieldErrors()) { // 어느 변수에서 터졌는지
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            // throw에서 Data 리턴인지, html 리턴인지 구분해서 터트리기
            throw new CustomException(errorMap.toString());
        }
    }

}
