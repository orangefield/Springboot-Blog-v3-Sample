package site.orangefield.blogsample.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.orangefield.blogsample.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDetailRespDto {

    private Post post;
    private boolean isPageOwner; // getter가 변수명과 똑같이 나온다!
}
