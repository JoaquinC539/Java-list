package com.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
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
    public Object save(HttpServletRequest request,Model model) throws Exception{
        Usersj user=usersService.save(request);
        String contentHeader=request.getHeader("content-type");
        if(contentHeader.equals("application/json")){
            if(user==null){
                Map<String,String> body=new HashMap<String,String>();
                body.put("error", "user could not be saved");
                HttpHeaders headers=new HttpHeaders();
                headers.add("Content-Type", "application/json");
                ResponseEntity<Map<String,String>> resp=new ResponseEntity<Map<String,String>>(body,headers, HttpStatusCode.valueOf(500));
                return resp;
            }else{                
                HttpHeaders headers=new HttpHeaders();
                headers.add("Content-Type", "application/json");
                ResponseEntity<Usersj> resp=new ResponseEntity<Usersj>(user,headers, HttpStatusCode.valueOf(200));
                return resp;
            }
        }
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
