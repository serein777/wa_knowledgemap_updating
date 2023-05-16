package edu.hhu.wa_knowledgemap_updating.config;


import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //对那些请求路径有效
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(10000);
    }
}

