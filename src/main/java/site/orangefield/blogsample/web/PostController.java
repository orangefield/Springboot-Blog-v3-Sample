package site.orangefield.blogsample.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.config.auth.LoginUser;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.handler.ex.CustomException;
import site.orangefield.blogsample.service.PostService;
import site.orangefield.blogsample.web.dto.post.PostRespDto;
import site.orangefield.blogsample.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    @PostMapping("/s/post")
    public String write(PostWriteReqDto postWriteReqDto, @AuthenticationPrincipal LoginUser loginUser) {

        postService.게시글쓰기(postWriteReqDto, loginUser.getUser());

        return "redirect:/user/" + loginUser.getUser().getId() + "/post";
    }

    @GetMapping("/s/post/write-form")
    public String writeForm(@AuthenticationPrincipal LoginUser loginUser, Model model) {
        List<Category> categories = postService.게시글쓰기화면(loginUser.getUser());

        if (categories.size() == 0) {
            throw new CustomException("카테고리 등록이 필요합니다");
        }

        model.addAttribute("categories", categories);

        return "/post/writeForm";
    }

    @GetMapping("/user/{id}/post")
    public String postList(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser, Model model) {

        // SELECT * FROM category WHERE userId = :id
        // 게시글 목록을 볼 때 카테고리를 가져가기
        PostRespDto postRespDto = postService.게시글목록보기(id);
        model.addAttribute("postRespDto", postRespDto);

        return "/post/list";
    }
}
