package site.orangefield.blogsample.domain.category;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import site.orangefield.blogsample.domain.user.User;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "category_uk", columnNames = { "title", "userId" })
}) // 카테고리의 title과 User를 묶어서 unique
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // UTF-8 (가변 인코딩 : 영어 1Byte, 한글 3Byte)
    @Column(length = 60, nullable = false)
    private String title;

    @JoinColumn(name = "userId")
    @ManyToOne
    private User user;

    @CreatedDate // insert할 때 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update할 때 동작
    private LocalDateTime updateDate;
}
