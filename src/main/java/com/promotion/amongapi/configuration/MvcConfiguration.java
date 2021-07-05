package com.promotion.amongapi.configuration;

import com.promotion.amongapi.intercepter.AuthorizeKeyInterceptor;
import com.promotion.amongapi.service.AuthorizeKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfiguration implements WebMvcConfigurer {
    private final AuthorizeKeyService service;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizeKeyInterceptor(service))
                .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
    }
}
