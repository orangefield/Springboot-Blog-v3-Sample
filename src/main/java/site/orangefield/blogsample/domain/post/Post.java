package site.orangefield.blogsample.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.user.User;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String content;
    private String thumnail;

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
}