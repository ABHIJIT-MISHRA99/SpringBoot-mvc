package com.abhijit.accountsvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    private AppInterceptor appInterceptor;

    public WebMvcConfig(AppInterceptor appInterceptor){
        this.appInterceptor=appInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("appInterceptor:"+appInterceptor);
        registry.addInterceptor(appInterceptor).addPathPatterns("/**");
    }
}
