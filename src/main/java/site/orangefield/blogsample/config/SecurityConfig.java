package site.orangefield.blogsample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import site.orangefield.blogsample.handler.LoginSuccessHandler;

@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean // 시큐리티는 비번이 암호화되지 않으면 로그인 안 시켜줌!
    public BCryptPasswordEncoder encode() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http); // 시큐리티 비활성화
        http.csrf().disable(); // 이거 안하면 postman 테스트 못함
        http.authorizeRequests()
                .antMatchers("/s/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login-form")
                // .usernameParameter("uname") // Security가 확인하는 키값을 바꾸고 싶을 때
                // .passwordParameter("pwd")
                .loginProcessingUrl("/login") // /login 요청이 오면 로그인 프로세스 자동 진행(x-www-form-urlencoded)
                // .failureHandler(null)
                .successHandler(new LoginSuccessHandler()); // 로그인 성공하면
    }

}
