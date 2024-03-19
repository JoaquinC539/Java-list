package com.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.project.models.Roles;
import com.project.models.Usersj;
import com.project.services.UsersjService;
import com.project.utils.Session;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UsersjService usersService;

    private Session session=new Session();

    @GetMapping()
    public String register(Model model){
        List<String> rolesList=Arrays.stream(Roles.values())
            .map(Enum::name)
            .collect(Collectors.toList());
        model.addAttribute("roles", rolesList);
        model.addAttribute("sessions", session);
        return "register/register";
    }

    @PostMapping()
    public RedirectView save(HttpServletRequest request,Model model) throws Exception{
        Usersj user=usersService.save(request);
        if(user==null){
            session.setError("Error creado a usuario nuevo");
        }else{
            session.setSuccess("Usuario creado exitosamente");
        }
        return new RedirectView("/register",true);
    }
    
    @GetMapping("/test")
    @ResponseBody
    public Usersj test(){
        return usersService.test();
    }


}
