package xyz.hohoon.book.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${kakao.header.authorization}")
    private String kakaoAuthHeader;
    @Value("${naver.header.client-id}")
    private String naverClientId;
    @Value(("${naver.header.client-secret}"))
    private String naverClientSecret;

    @Bean("kakaoHttpHeaders")
    public HttpHeaders kakaoHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, kakaoAuthHeader);

        return headers;
    }

    @Bean("naverHttpHeaders")
    public HttpHeaders naverHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", naverClientId);
        headers.add("X-Naver-Client-Secret", naverClientSecret);

        return headers;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // RequestMappingHandlerMapping 에서 GET 이 없다고 405 에러가 발생해서, 우선순위를 높여서 먼저 처리하기 위해 설정.
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry
                .addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
        registry
                .addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
