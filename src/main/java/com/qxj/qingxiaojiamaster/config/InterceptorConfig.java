package com.qxj.qingxiaojiamaster.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : 15754
 * @version 1.0.0
 * @since : 2023/5/11 14:46
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**") //拦截所有请求
                .excludePathPatterns("/admin/login"); //放行登录请求
    }
}