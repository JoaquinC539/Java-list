package com.project.controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.project.services.auth.AuthPayload;
import com.project.services.auth.AuthService;
import com.project.utils.JwtUtil;
import com.project.utils.RequestParser;
import com.project.utils.Session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private Session session=new Session();
    @Autowired
    AuthService authService;
    
   

    @GetMapping()
    public Object login(HttpServletRequest request,Model model){
        Jws<Claims> clJws=JwtUtil.descipherAuthToken(request);
        if(clJws==null){
            model.addAttribute("sessions", session);
            return "login/login";
        }else if(clJws.getPayload().containsKey("id")){
            return new RedirectView("/dashboard");
        }
        else{
            model.addAttribute("sessions", session);
            return "login/login"; 
        }
        
    }
    
    @PostMapping()
    public Object log(HttpServletRequest request,HttpServletResponse response) throws Exception{
        LinkedHashMap<String,Object> body=RequestParser.parseBody(request);
        
        AuthPayload auth=authService.auth(body);
        if(auth==null){
            session.setError("Authorization couldn't not be fulfilled try again");
            return new RedirectView("login");
        }
        if(!auth.getAuth()){
            session.setError("Contraseña incorrecta");
            return new RedirectView("login");
        }else{            
            String token=JwtUtil.generateAuthToken(auth.getUser());
            if(token==null){
                session.setError("Error at generating auth token");
               return  new RedirectView("login");
            }
            String refreshToken=JwtUtil.generateRefreshToken(auth.getUser());
            if(refreshToken==null){
                session.setError("Error at generating refresh token");
               return  new RedirectView("login");
            }            
            String authToken=JwtUtil.generateAuthToken(auth.getUser());
            if(authToken==null){
                session.setError("Error at generatin auth token");
                return new RedirectView("login");
            }
            HttpHeaders headers=new HttpHeaders();
            Integer refreshDuration=10*7*24*60*60;
            Integer authDuration=1*60*60;
            Cookie refreshCookie=new Cookie("refreshAuth", refreshToken);
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(refreshDuration);
            refreshCookie.setAttribute("SameSite", "Strict");
            Cookie authCookie=new Cookie("Auth", authToken);
            authCookie.setHttpOnly(false);
            authCookie.setMaxAge(authDuration);
            authCookie.setAttribute("SameSite", "Strict");
            response.addCookie(authCookie);
            response.addCookie(refreshCookie);
            String contentType = request.getContentType();
            if(contentType != null && contentType.contains("application/json")) {
                LinkedHashMap<String,Object> res=new LinkedHashMap<>();                
                res.put("Auth", token);
                res.put("refreshAuth", refreshToken);
                ResponseEntity<LinkedHashMap<String,Object>> resp=new ResponseEntity<>(res, headers, 200);            
                return resp;
            }else{
                headers.add("Location", "/dashboard");
                return  new ResponseEntity<>(headers, HttpStatus.FOUND);
            }
        }
    }
}
