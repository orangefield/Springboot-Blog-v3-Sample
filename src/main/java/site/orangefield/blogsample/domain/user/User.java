package site.orangefield.blogsample.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    // 1234 -> SHA256(해시 알고리즘) 암호화 -> 안하면 security가 거부함
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 60, nullable = false)
    private String email;

    @Column(nullable = true)
    private String profileImg;

    @CreatedDate // insert할 때 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update할 때 동작
    private LocalDateTime updateDate;
}
