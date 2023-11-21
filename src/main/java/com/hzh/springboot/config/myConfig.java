package com.hzh.springboot.config;

import com.hzh.springboot.interceptor.myInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class myConfig implements WebMvcConfigurer {

    @Autowired
    private myInterceptor mInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/","/login","/initRedis","/css/**","/fonts/**","/images/**","/js/**")
                ;
    }

}
