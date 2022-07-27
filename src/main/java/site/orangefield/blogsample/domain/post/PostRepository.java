package site.orangefield.blogsample.domain.post;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    // 좋아요 기능 구현하고 쿼리 변경
    @Query(value = "SELECT * FROM post ORDER BY id DESC LIMIT 0,9", nativeQuery = true)
    List<Post> mFindByPopular();

    @Query(value = "SELECT * FROM post WHERE userId = :userId ORDER BY id DESC", nativeQuery = true)
    Page<Post> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM post WHERE userId = :userId AND categoryId = :categoryId ORDER BY id DESC", nativeQuery = true)
    Page<Post> findByUserIdAndCategoryId(@Param("userId") Integer userId, @Param("categoryId") Integer categoryId,
            Pageable pageable);

    @Modifying // INSERT, UPDATE, DELETE 할 때는 꼭 붙여줘야
    @Query(value = "INSERT INTO post(categoryId, title, content, userId, thumbnail, createDate, updateDate) VALUES(:categoryId, :title, :content, :userId, :thumbnail, :now(), :now())", nativeQuery = true)
    void mSave(Integer categoryId, Integer userId, String title, String content, String thumbnail);
}
