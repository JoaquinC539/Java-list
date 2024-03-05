package com.project.controllers;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor {
    
    @ExceptionHandler({NoHandlerFoundException.class,NoResourceFoundException.class})    
    public Object handleNotFound(Exception ex, HttpServletRequest request){
        String contentHeader=request.getHeader("Content-Type");
        String uri = request.getRequestURI();
        String method = request.getMethod();
        if(contentHeader==null){
            return "helpers/notFound";
        }else{
            HashMap<String,String> response=new HashMap<String,String>();
            response.put("uri", uri);
            response.put("method",method);
            response.put("Error","404 Not Found");
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", "application/json");
            return new ResponseEntity<>(response,headers, HttpStatusCode.valueOf(404));
        }
    }
}
