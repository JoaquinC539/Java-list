package com.project.controllers;

import java.util.LinkedHashMap;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.project.models.Proveedor;
import com.project.services.ProveedorService;
import com.project.utils.PaginatedData;
import com.project.utils.RequestParser;
import com.project.utils.Session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/proveedor")
public class ProveedorController {
    private Session session = new Session();
    private final ProveedorService proveedorService;
    public ProveedorController(ProveedorService proveedorService){
        this.proveedorService=proveedorService;
    }
    @GetMapping()
    public Object index (HttpServletRequest request, HttpServletResponse response){
        PaginatedData<Proveedor> proveedores=proveedorService.index(request);
        String contentHeader = request.getHeader("content-type");
        if (contentHeader == null) {
            ModelAndView model = new ModelAndView();
            model.setViewName("proveedor/index");
            model.addObject("proveedores", proveedores);
            model.addObject("sessions", session);
            return model;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            ResponseEntity<PaginatedData<Proveedor>> res = new ResponseEntity<>(proveedores, headers,
                    HttpStatusCode.valueOf(200));
            return res;
        }
    }
    @GetMapping("/create")
    public Object create (Model model){
        model.addAttribute("sessions", session);
        return "proveedor/create";
    }
    @PostMapping()
    public Object save(HttpServletRequest request) throws Exception{
        LinkedHashMap<String, Object> body = RequestParser.parseBody(request);
        Proveedor proveedor = proveedorService.save(body);
        if(proveedor==null){
            session.setError("Error al crear proveedor");
            return new RedirectView("/proveedor/create",true);
        }
        String contentHeader = request.getHeader("content-type");
        if (contentHeader.equals("application/x-www-form-urlencoded")) {
            session.setSuccess("Proveedor creado con exito");
            return new RedirectView("/proveedor/edit/" + proveedor.get_id(), true);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            ResponseEntity<Proveedor> response = new ResponseEntity<>(proveedor, headers,
                    HttpStatusCode.valueOf(200));
            return response;
        }
    }
    @GetMapping(value = "/edit/{id}")
    public Object edit(@PathVariable("id") Integer id, Model model) {
        Optional<Proveedor> proveedorOptional=proveedorService.get(id);
        if (proveedorOptional.isPresent()) {
            model.addAttribute("sessions", session);
            Proveedor proveedor = proveedorOptional.get();
            model.addAttribute("proveedor", proveedor);
            return "proveedor/edit";
        } else {
            session.setError("Proveedor no encontrado");
            return new RedirectView("/proveedor", true);
        }
    }
    @PutMapping(value = "/{id}")
    public Object update(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
        LinkedHashMap<String, Object> body = RequestParser.parseBody(request); 
        Proveedor proveedor = proveedorService.update(id, body);        
        if (proveedor == null) {
            session.setError("Proveedor no encontrado para actualizar");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<Proveedor> response = new ResponseEntity<>(proveedor, headers,
                HttpStatusCode.valueOf(200));
        session.setSuccess("Proveedor actualizado con exito");
        return response;

    }

}
