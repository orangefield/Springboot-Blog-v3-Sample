package site.orangefield.blogsample.domain.love;

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

import site.orangefield.blogsample.domain.post.Post;
import site.orangefield.blogsample.domain.user.User;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Love {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "postId")
    @ManyToOne
    private Post post;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @CreatedDate // insert할 때 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update할 때 동작
    private LocalDateTime updateDate;
}