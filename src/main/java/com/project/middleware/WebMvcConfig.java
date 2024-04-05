package com.project.middleware;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtInterceptor jwtInterceptor;
    private List<String> routeExceptions=new ArrayList<String>();
    private List<String> protectedRoutes=new ArrayList<String>();
    
    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        routeExceptions.add("/login");
        routeExceptions.add("/register");
        routeExceptions.add("/css/**");
        routeExceptions.add("/js/**");
        routeExceptions.add("/favicon.ico");
        protectedRoutes.add("/**");
        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns(protectedRoutes)
            .excludePathPatterns(routeExceptions);
    }
    
    
}
