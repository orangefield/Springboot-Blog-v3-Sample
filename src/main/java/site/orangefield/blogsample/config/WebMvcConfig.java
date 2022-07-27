package site.orangefield.blogsample.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // web.xml 설정파일 (POJO)

    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/upload/**") // 이 패턴의 경로를 쓰면
                .addResourceLocations("file:///" + uploadFolder) // (file프로토콜은 :/// 사용) 이 폴더로 들어간다
                .setCachePeriod(60 * 60) // 파일을 캐싱하는 주기, 초 단위 (=1h)
                .resourceChain(true) // 캐싱된 파일을 쓸 수 있는가
                .addResolver(new PathResourceResolver());
    }

}
