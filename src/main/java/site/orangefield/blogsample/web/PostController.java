package site.orangefield.blogsample.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.config.auth.LoginUser;

@RequiredArgsConstructor
@Controller
public class PostController {

    @GetMapping("user/{id}/post")
    public String postList(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser) {
        return "/post/list";
    }
}
