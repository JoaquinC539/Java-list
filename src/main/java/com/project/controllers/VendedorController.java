package com.project.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.models.Vendedor;
import com.project.services.VendedorService;
import com.project.utils.PaginatedData;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/vendedor")
public class VendedorController {
    @Autowired
    private VendedorService vendedorService;

    @GetMapping(value = "")
    public Object index(HttpServletRequest request){
        PaginatedData<Vendedor> vendedores = vendedorService.index(request);
        String contentHeader=request.getHeader("content-type");
        if(contentHeader==null){  
            ModelAndView model=new ModelAndView();
            model.setViewName("vendedor/index");            
            model.addObject("vendedores", vendedores);      
            return model;
        }else{
            HttpHeaders headers=new HttpHeaders();            
            headers.add("Content-Type", "application/json");
            ResponseEntity response=new ResponseEntity<>(vendedores,headers,HttpStatusCode.valueOf(200));         
            return response;
        }        
    }
    
}
