package com.galdon.rrp.config;

import com.galdon.rrp.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @program: rrp
 * @description: WebMvc 配置类
 * @author: galdon
 * @create: 2019-08-15 20:08
 **/
@Configuration
public class MyWebAppConfig extends WebMvcConfigurationSupport {

    @Bean
    TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    /**
     * 拦截器对 Url 的过滤设置
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/refresh");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }
}
