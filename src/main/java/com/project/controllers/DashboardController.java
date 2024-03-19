package com.project.controllers;

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
    public String index(HttpServletRequest request,Model model){
        Usersj user=(Usersj) request.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("sessions", session);
        return "home/dashboard";
    }
}
