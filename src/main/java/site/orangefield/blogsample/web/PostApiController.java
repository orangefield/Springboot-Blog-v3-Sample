package site.orangefield.blogsample.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.config.auth.LoginUser;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.service.PostService;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostService postService;

    @DeleteMapping("/s/api/post/{id}")
    public ResponseEntity<?> postDelete(@PathVariable Integer id, @AuthenticationPrincipal LoginUser loginUser) {

        User principal = loginUser.getUser();
        postService.게시글삭제(id, principal);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
