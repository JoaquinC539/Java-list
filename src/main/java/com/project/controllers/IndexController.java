package com.project.controllers;



import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedHashMap;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.project.utils.Utils;

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
    public ResponseEntity<Object> postTest(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {         
        LinkedHashMap<String,Object> response=Utils.JSONParse("{\"field1\":\"value1\"}");
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(200));
    }
}
