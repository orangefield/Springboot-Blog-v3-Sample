package site.orangefield.blogsample.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.service.UserService;
import site.orangefield.blogsample.util.UtilFileUpload;
import site.orangefield.blogsample.util.UtilValid;
import site.orangefield.blogsample.web.dto.user.JoinReqDto;
import site.orangefield.blogsample.web.dto.user.PasswordResetReqDto;

@RequiredArgsConstructor
@Controller
public class UserController {

    // DI
    private final UserService userService;

    @Value("${file.path}")
    private String uploadFolder;

    @PutMapping("/s/api/user/{id}/profile-img")
    public ResponseEntity<?> profileImgUpdate(MultipartFile profileImg) {
        // UtilFileUpload.write(uploadFolder, profileImg);
        // userService.프로필이미지변경();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "/user/loginForm";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "/user/joinForm";
    }

    // ResponseEntity는 @ResponseBody를 붙이지 않아도 data 리턴
    @GetMapping("/api/user/username-same-check")
    public ResponseEntity<?> usernameSameCheck(String username) {
        boolean isNotSame = userService.유저네임중복체크(username); // true(같지 않다)
        return new ResponseEntity<>(isNotSame, HttpStatus.OK);
    }

    @GetMapping("/user/password-reset-form")
    public String passwordResetForm() {
        return "/user/passwordResetForm";
    }

    @PostMapping("/user/password-reset")
    public String passwordReset(@Valid PasswordResetReqDto passwordResetReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        // 핵심 로직
        userService.패스워드초기화(passwordResetReqDto);

        return "redirect:/login-form";
    }

    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        UtilValid.요청에러처리(bindingResult);

        // 핵심 로직
        userService.회원가입(joinReqDto.toEntity());

        return "redirect:/login-form";
    }

    @GetMapping("/s/user/{id}")
    public String updateForm(@PathVariable Integer id) {
        return "/user/updateForm";
    }
}
