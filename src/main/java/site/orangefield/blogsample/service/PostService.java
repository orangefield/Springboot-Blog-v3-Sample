package site.orangefield.blogsample.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.category.CategoryRepository;
import site.orangefield.blogsample.domain.post.Post;
import site.orangefield.blogsample.domain.post.PostRepository;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.handler.ex.CustomException;
import site.orangefield.blogsample.util.UtilFileUpload;
import site.orangefield.blogsample.web.dto.post.PostRespDto;
import site.orangefield.blogsample.web.dto.post.PostWriteReqDto;

@RequiredArgsConstructor
@Service
public class PostService {

    @Value("${file.path}")
    private String uploadFolder;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public List<Category> 게시글쓰기화면(User principal) {
        return categoryRepository.findByUserId(principal.getId());
    }

    @Transactional
    public void 게시글쓰기(PostWriteReqDto postWriteReqDto, User principal) {
        // 1. 이미지 파일 저장(UUID로 변경해서)
        String thumbnail = null;
        if (!postWriteReqDto.getThumbnailFile().isEmpty()) {
            thumbnail = UtilFileUpload.write(uploadFolder, postWriteReqDto.getThumbnailFile());
        }

        // 2. 카테고리가 있는지 확인
        Optional<Category> categoryOp = categoryRepository.findById(postWriteReqDto.getCategoryId());

        // 3. post DB 저장
        if (categoryOp.isPresent()) {
            Post post = postWriteReqDto.toEntity(thumbnail, principal, categoryOp.get());
            postRepository.save(post);
        } else {
            throw new CustomException("해당 카테고리가 존재하지 않습니다");
        }
    }

    public PostRespDto 게시글목록보기(Integer userId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserId(userId, pageable);
        List<Category> categoriesEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categoriesEntity);

        return postRespDto;
    }

    public PostRespDto 게시글카테고리별보기(Integer userId, Integer categoryId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        List<Category> categoriesEntity = categoryRepository.findByUserId(userId);

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categoriesEntity);

        return postRespDto;
    }
}
