package com.syh.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 处理跨域请求
 * @author 孙远航
 * @version 1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原生域
                .allowedOriginPatterns("*")
                .allowedMethods(new String[]{"GET","POST","PUT","DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
