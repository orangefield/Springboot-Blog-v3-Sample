package site.orangefield.blogsample.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.handler.ex.CustomException;
import site.orangefield.blogsample.service.UserService;
import site.orangefield.blogsample.web.dto.JoinReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    // DI
    private final UserService userService;

    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>(); // 에러는 여러 개
            for (FieldError fe : bindingResult.getFieldErrors()) { // 어느 변수에서 터졌는지
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            // throw에서 Data 리턴인지, html 리턴인지 구분해서 터트리기
            throw new CustomException(errorMap.toString());
        }

        // 핵심 로직
        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
    }
}
