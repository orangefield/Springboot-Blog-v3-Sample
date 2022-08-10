package site.orangefield.blogsample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.category.CategoryRepository;
import site.orangefield.blogsample.domain.post.Post;
import site.orangefield.blogsample.domain.post.PostRepository;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.domain.user.UserRepository;
import site.orangefield.blogsample.domain.visit.Visit;
import site.orangefield.blogsample.domain.visit.VisitRepository;
import site.orangefield.blogsample.handler.ex.CustomApiException;
import site.orangefield.blogsample.handler.ex.CustomException;
import site.orangefield.blogsample.util.UtilFileUpload;
import site.orangefield.blogsample.util.UtilPost;
import site.orangefield.blogsample.web.dto.post.PostDetailRespDto;
import site.orangefield.blogsample.web.dto.post.PostRespDto;
import site.orangefield.blogsample.web.dto.post.PostWriteReqDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    @Value("${file.path}")
    private String uploadFolder;

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    @Transactional
    public void 게시글삭제(Integer id, User principal) {
        // 게시글 확인
        Post postEntity = UtilPost.postFindById(id);

        // 권한 체크
        if (UtilPost.authCheck(postEntity.getId(), principal.getId())) {
            // 게시글 삭제
            postRepository.deleteById(id);
        } else {
            throw new CustomApiException("삭제 권한이 없습니다");
        }

    }

    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id) {

        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        // 게시글 찾기
        Post postEntity = UtilPost.postFindById(id);

        // 방문자수 증가
        UtilPost.visitIncrease(postEntity.getUser().getId());

        // 리턴값 만들기
        postDetailRespDto.setPost(postEntity);
        postDetailRespDto.setPageOwner(false);

        return postDetailRespDto;
    }

    @Transactional
    public PostDetailRespDto 게시글상세보기(Integer id, User principal) {
        // 1. 권한체크
        // 2. 게시글 가져오기
        // 3. 방문자수 증가
        // 4. 리턴값 만들기

        PostDetailRespDto postDetailRespDto = new PostDetailRespDto();

        // 게시글 가져오기
        Post postEntity = UtilPost.postFindById(id);

        // 권한체크
        boolean isAuth = UtilPost.authCheck(postEntity.getId(), principal.getId());

        // 방문자수 증가
        UtilPost.visitIncrease(postEntity.getUser().getId());

        // 리턴값 만들기
        postDetailRespDto.setPost(postEntity);
        postDetailRespDto.setPageOwner(isAuth);

        return postDetailRespDto;
    }

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

    @Transactional
    public PostRespDto 게시글목록보기(Integer pageOwnerId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserId(pageOwnerId, pageable);
        List<Category> categoriesEntity = categoryRepository.findByUserId(pageOwnerId);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categoriesEntity,
                pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1,
                pageNumbers,
                0L);

        // 방문자 카운터 증가
        UtilPost.visitIncrease(pageOwnerId);

        return postRespDto;
    }

    public PostRespDto 게시글카테고리별보기(Integer pageOwnerId, Integer categoryId, Pageable pageable) {
        Page<Post> postsEntity = postRepository.findByUserIdAndCategoryId(pageOwnerId, categoryId, pageable);
        List<Category> categoriesEntity = categoryRepository.findByUserId(pageOwnerId);

        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 0; i < postsEntity.getTotalPages(); i++) {
            pageNumbers.add(i);
        }

        PostRespDto postRespDto = new PostRespDto(
                postsEntity,
                categoriesEntity,
                pageOwnerId,
                postsEntity.getNumber() - 1,
                postsEntity.getNumber() + 1,
                pageNumbers,
                0L);

        // 방문자 카운터 증가
        UtilPost.visitIncrease(pageOwnerId);

        return postRespDto;
    }
}
