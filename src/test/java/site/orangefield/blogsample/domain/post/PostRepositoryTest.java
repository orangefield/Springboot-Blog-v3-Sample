package site.orangefield.blogsample.domain.post;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private EntityManager em;

    // 메서드 실행시마다 auto_increment 초기화
    @BeforeEach
    public void db_init() {
        postRepository.deleteAll();
        em
                .createNativeQuery("ALTER TABLE post ALTER COLUMN id RESTART WITH 1")
                .executeUpdate();
    }

    public void findByUserId_테스트() {
        // given
        String title = "더미제목";
        String content = "더미내용";
        Integer categoryId = 1;

        // when

        // then

    }
}
