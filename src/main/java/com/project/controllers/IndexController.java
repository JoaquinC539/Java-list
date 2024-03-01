package com.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class IndexController {
    
    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("successMessage", "!Operacion exitosa");
        
        return "index";
        
    }
    
    
}
