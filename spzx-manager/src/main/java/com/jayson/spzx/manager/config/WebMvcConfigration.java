package com.jayson.spzx.manager.config;

import com.jayson.spzx.manager.interceptor.LoginAuthInterceptor;
import com.jayson.spzx.manager.properties.UserAuthProperties;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Jayson_Y
 * @date: 2024/8/25
 * @project: spzx-parent
 */
@Component
public class WebMvcConfigration implements WebMvcConfigurer {

    @Resource
    private LoginAuthInterceptor loginAuthInterceptor;

    @Resource
    private UserAuthProperties userAuthProperties;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
//                .excludePathPatterns("/admin/system/index/login",
//                        "/admin/system/index/generateValidateCode")
                .excludePathPatterns(userAuthProperties.getNoAuthUrls())
                .addPathPatterns("/**");

    }
    /**
     * 跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")            // 添加规则路径
                .allowCredentials(true)                 // 是否允许在跨域情况下传递Cookie
                .allowedOriginPatterns("*")             // 允许请求来源的域规则
                .allowedHeaders("*")                    // 允许所有的请求头
                .allowedMethods("*");
    }
}
