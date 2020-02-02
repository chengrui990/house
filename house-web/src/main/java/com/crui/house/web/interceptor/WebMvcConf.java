package com.crui.house.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * VM Args:
 *
 * @author crui
 */
@Configuration
public class WebMvcConf extends WebMvcConfigurationSupport {
    @Autowired
    private AuthActionInterceptor authActionInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置静态资源处理
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
        registry.addInterceptor(authActionInterceptor).addPathPatterns("/accounts/profile");
        super.addInterceptors(registry);
    }
}
