package com.project.controllers;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.models.Usersj;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController()
@RequestMapping("/api/user")
public class UsersjController {
    public UsersjController(){
    }

    @GetMapping()
    public Object user (HttpServletRequest request,HttpServletResponse response){
        Usersj user=(Usersj) request.getAttribute("user");
        if(user==null){
            HashMap<String,String> body=new HashMap<String,String>();
            body.put("error", "User null");
            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-Type", "application/json");
            ResponseEntity<HashMap<String,String>> resp=new ResponseEntity<HashMap<String,String>>(body, headers, HttpStatusCode.valueOf(500));
            return resp;
        }else{
            response.addHeader("Content-Type","application/json");
            response.setStatus(200);
            return user;
        }
       
    }
}
