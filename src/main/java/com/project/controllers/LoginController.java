package com.project.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.project.utils.Session;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private Session session=new Session();
   

    @GetMapping()
    public String login(Model model){
        
        
        model.addAttribute("sessions", session);
        return "login/login";
    }
    
    
}
