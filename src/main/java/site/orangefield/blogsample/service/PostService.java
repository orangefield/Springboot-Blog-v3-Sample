package site.orangefield.blogsample.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.category.CategoryRepository;
import site.orangefield.blogsample.domain.post.Post;
import site.orangefield.blogsample.domain.post.PostRepository;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.web.dto.post.PostRespDto;
import site.orangefield.blogsample.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public List<Category> 게시글쓰기화면(User principal) {
        return categoryRepository.findByUserId(principal.getId());
    }

    public void 게시글쓰기(PostWriteReqDto postWriteReqDto) {
        // 1. 이미지 파일 저장(UUID로 변경해서)
        // 2. 이미지 파일명을 Post 오브젝트의 thumbnail로 옮기기
        // 3. title, content도 Post 오브젝트에 옮기기
        // 4. userId를 Post 오브젝트에 옮기기
        // 5. categoryId를 Post 오브젝트에 옮기기
        // 6. save
    }

    public PostRespDto 게시글목록보기(int userId) {
        List<Post> postsEntity = postRepository.findByUserId(userId);
        List<Category> categoriesEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categoriesEntity);

        return postRespDto;
    }
}
