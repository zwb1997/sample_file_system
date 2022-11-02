package com.filesystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.filesystem.interceptors.CustomInterceptor;

@Configuration
@EnableWebMvc
public class CustomMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/**");
    }

    // @Override
    // public void configureContentNegotiation(ContentNegotiationConfigurer
    // configurer) {
    // configurer.mediaType("json", MediaType.APPLICATION_JSON);
    // configurer.mediaType("xml", MediaType.APPLICATION_XML);
    // configurer.mediaType("pdf", MediaType.APPLICATION_PDF);
    // }
}
