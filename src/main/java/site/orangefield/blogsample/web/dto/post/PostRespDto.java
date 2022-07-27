package site.orangefield.blogsample.web.dto.post;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.post.Post;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRespDto {

    private Page<Post> posts;
    private List<Category> categories;
}
