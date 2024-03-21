package com.project.controllers;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.services.auth.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    @Autowired
    AuthService authService;

    @PostMapping
    public Object logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contentType = request.getContentType();
        Cookie refreshCookie = new Cookie("refreshAuth", "Removed_refresh");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setMaxAge(0);
        refreshCookie.setAttribute("SameSite", "Strict");
        Cookie authCookie = new Cookie("Auth", "Removed_auth");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(0);
        authCookie.setAttribute("SameSite", "Strict");
        response.addCookie(authCookie);
        response.addCookie(refreshCookie);
        request.setAttribute("user", null);
        HttpHeaders headers = new HttpHeaders();

        if (contentType != null && contentType.contains("application/json")) {
            LinkedHashMap<String, Object> res = new LinkedHashMap<>();
            res.put("Auth", "Removed_Auth");
            res.put("refreshAuth", "Removed_Refresh");
            ResponseEntity<LinkedHashMap<String, Object>> resp = new ResponseEntity<>(res, headers, 200);
            return resp;
        } else {
            headers.add("Location", "/login");
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
    }
}
