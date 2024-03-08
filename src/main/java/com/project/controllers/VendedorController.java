package com.project.controllers;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.models.Vendedor;
import com.project.services.VendedorService;
import com.project.utils.PaginatedData;
import com.project.utils.RequestParser;
import com.project.utils.Session;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/vendedor")

public class VendedorController {
    private Session session=new Session();
    @Autowired
    private VendedorService vendedorService;

    @GetMapping(value = "")
    public Object index(HttpServletRequest request) {
        PaginatedData<Vendedor> vendedores = vendedorService.index(request);
        String contentHeader = request.getHeader("content-type");
        System.out.println(session);        
        if (contentHeader == null) {            
            ModelAndView model = new ModelAndView();
            model.setViewName("vendedor/index");
            model.addObject("vendedores", vendedores);
            model.addObject("sessions", session);  
            return model;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            ResponseEntity<PaginatedData<Vendedor>> response = new ResponseEntity<>(vendedores, headers,
                    HttpStatusCode.valueOf(200));
            return response;
        }
    }

    @GetMapping(value = "/create")
    public String create(Model model) {
        return "vendedor/create";
    }

    @PostMapping(value = "")
    public Object save(HttpServletRequest request) throws Exception {
        LinkedHashMap<String, Object> body = RequestParser.parseBody(request);
        Vendedor vendedor = vendedorService.save(body);
        String contentHeader = request.getHeader("content-type");
        if (contentHeader.equals("application/x-www-form-urlencoded")) {
            session.setSuccess("Vendedor creado con exito");
            return new RedirectView("/vendedor", true);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            
            ResponseEntity<Vendedor> response = new ResponseEntity<>(vendedor, headers,
                    HttpStatusCode.valueOf(200));
            return response;
        }
    }

}
