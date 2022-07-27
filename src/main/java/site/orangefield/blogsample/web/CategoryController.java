package site.orangefield.blogsample.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.config.auth.LoginUser;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.service.CategoryService;
import site.orangefield.blogsample.util.Script;
import site.orangefield.blogsample.util.UtilValid;
import site.orangefield.blogsample.web.dto.category.CategoryWriteReqDto;

@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final HttpSession session;
    private final CategoryService categoryService;

    @GetMapping("/s/category/writeForm")
    public String writeForm() {
        return "/category/writeForm";
    }

    @PostMapping("/s/category")
    public @ResponseBody String write(
            @Valid CategoryWriteReqDto categoryWriteReqDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal LoginUser loginUser) {

        UtilValid.요청에러처리(bindingResult);

        // User principal = (User) session.getAttribute("principal");
        // System.out.println("==============================");
        // loginUser = (LoginUser)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User principal = loginUser.getUser();
        Category category = categoryWriteReqDto.toEntity(principal);

        categoryService.카테고리등록(category);

        return Script.href("/s/category/writeForm", "카테고리 등록 완료");
    }
}
