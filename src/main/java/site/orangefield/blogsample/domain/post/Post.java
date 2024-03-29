package site.orangefield.blogsample.domain.post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.user.User;
import site.orangefield.blogsample.util.UtilPost;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 60, nullable = false)
    private String title;

    @Lob
    @Column(nullable = true)
    private String content;

    @Column(length = 200, nullable = true)
    private String thumbnail;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @JoinColumn(name = "categoryId")
    @ManyToOne
    private Category category;

    @CreatedDate // insert할 때 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update할 때 동작
    private LocalDateTime updateDate;

    // yyyy-MM-dd HH:mm:ss
    public String getFormatCreateDate() { // mustache는 getter를 찾는다
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm");
        return createDate.format(formatter);
    }

    public String getFormatContent() {
        return UtilPost.getContentWithoutImg(content);
    }
}
