package site.orangefield.blogsample.domain.post;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(value = "SELECT * FROM post WHERE userId = :userId", nativeQuery = true)
    List<Post> findByUserId(@Param("userId") Integer userId);

    @Query(value = "INSERT INTO post(categoryId, title, content, userId, thumbnail, createDate, updateDate) VALUES (:categoryId, :title, :content, :userId, :thumbnail, :now(), :now())", nativeQuery = true)
    void mSave(Integer categoryId, Integer userId, String title, String content, String thumbnail);
}
