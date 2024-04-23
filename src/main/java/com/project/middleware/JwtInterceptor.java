package com.project.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.services.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    AuthService authService;
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull Object handler) throws Exception{
        Boolean authSetter=authService.checkTokens(request, response);        
        String contentType = request.getContentType();
        String appHeader=request.getHeader("app-header");
        if(authSetter==null){
            return false;
        }
        if(contentType!=null && contentType.equals("application/json")){
            if(!authSetter && !request.getRequestURI().equals("/login")){
                
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
                response.setContentType("application/json");
                response.getWriter().write("Unauthorized:Authentication required"); 
                response.getWriter().flush(); 
                return false;
            }else{
                return true;
            }
        }
        if(appHeader!=null && appHeader.equals("http/json")){
            if(!authSetter && !request.getRequestURI().equals("/login")){
                
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
                response.setContentType("application/json");
                response.getWriter().write("Unauthorized:Authentication required"); 
                response.getWriter().flush(); 
                return false;
            }else{
                return true;
            }
        }
        
        if(!authSetter && !request.getRequestURI().equals("/login")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);           
            response.sendRedirect("/login");
            return false;
        }
             
        return true;
    }
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable ModelAndView modelAndView) throws Exception{
        
    }
}
