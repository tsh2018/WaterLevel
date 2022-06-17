package com.tsh.watermanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Copyright (C), 2022
 * Author: Tashany
 * Date: 2022/3/15 20:02
 * FileName: Tashany
 * Description: 解决跨域问题
 */
//@Configuration
public class VisitConfig {
    public class CorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    //是否发送Cookie
                    .allowCredentials(true)
                    //放行哪些原始域
                    .allowedOrigins("*")
                    .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                    .allowedHeaders("*")
                    .exposedHeaders("*");
        }
    }
}
