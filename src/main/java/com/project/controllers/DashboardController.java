package com.project.controllers;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.models.Usersj;
import com.project.utils.Session;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    Session session =new Session();
    
    @GetMapping()
    public Object index(HttpServletRequest request,Model model){
        String contentTypeHeader=request.getHeader("Content-Type");
         if(contentTypeHeader=="application/json"){
                HttpHeaders headers=new HttpHeaders();
                headers.add("Content-Type", contentTypeHeader);
                HashMap<String,String> body=new HashMap<>();
                body.put("message", "Login successful");
                return new ResponseEntity<>(body, headers, 301);            
            }  
        Usersj user=(Usersj) request.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("sessions", session);
        return "home/dashboard";
    }
}
