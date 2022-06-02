package site.orangefield.blogsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogsampleApplication.class, args);
	}

}
