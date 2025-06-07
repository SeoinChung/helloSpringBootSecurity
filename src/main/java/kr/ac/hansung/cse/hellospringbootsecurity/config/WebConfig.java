package kr.ac.hansung.cse.hellospringbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("home"); // 루트 URL 접근 시 home.html로 이동
        registry.addViewController("/login").setViewName("login"); // 로그인 페이지 뷰 이름 매핑
        registry.addViewController("/home").setViewName("home"); // /home 접근 시에도 home.html 반환
        registry.addViewController("/accessDenied").setViewName("403"); // 권한 없음 오류 발생 시 403.html

    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
}
