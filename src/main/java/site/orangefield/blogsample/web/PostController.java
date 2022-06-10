package site.orangefield.blogsample.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.config.auth.LoginUser;
import site.orangefield.blogsample.service.PostService;
import site.orangefield.blogsample.web.dto.post.PostRespDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @GetMapping("/user/{id}/post")
    public String postList(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser, Model model) {

        // SELECT * FROM category WHERE userId = :id
        // 게시글 목록을 볼 때 카테고리를 가져가기
        PostRespDto postRespDto = postService.게시글목록보기(id);
        model.addAttribute("postRespDto", postRespDto);

        return "/post/list";
    }
}
