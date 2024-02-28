package com.project.controllers;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.project.utils.RequestParser;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    
    @RequestMapping(value = "/test",method = RequestMethod.GET)    
    public ResponseEntity<Object> getTest(HttpServletRequest request) throws IOException{
        HttpHeaders headers=new HttpHeaders();
        headers.add("Test-Header", "Soy un header");
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("test","Esto es un test"); 
        
        ResponseEntity<Object> responseEntity=new ResponseEntity<Object>(map,headers,HttpStatusCode.valueOf(200));

        return responseEntity;
    }
    @PostMapping(value = "/testPost")
    public ResponseEntity<Object> postTest(HttpServletRequest request) throws IOException{
        HashMap<String,Object> body=RequestParser.parseBody(request);
        // System.out.println(request.getHeader("Content-Type"));
        
        System.out.println(body);

        LinkedHashMap<String,Object> response=new LinkedHashMap<String,Object>();
        response.put("post", "This is a post");
        return ResponseEntity.ok().body(response);
    }
}
