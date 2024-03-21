package com.project.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.project.models.Producto;
import com.project.models.Proveedor;
import com.project.repository.proveedor.ProveedorRepository;
import com.project.services.auth.ProductoService;
import com.project.utils.PaginatedDataGeneric;
import com.project.utils.RequestParser;
import com.project.utils.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/producto")
public class ProductoController {
    private Session session=new Session();
    
    @Autowired
    ProductoService productoService;

    @Autowired
    ProveedorRepository proveedorRepository;

    @GetMapping()
    public Object index (HttpServletRequest request, HttpServletResponse response){        
        PaginatedDataGeneric productos=productoService.index(request);
        String contentHeader = request.getHeader("content-type");
        if (contentHeader == null) {
            ModelAndView model = new ModelAndView();
            model.setViewName("producto/index");            
            model.addObject("productos", productos);
            model.addObject("sessions", session);
            List <Proveedor> proveedores=proveedorRepository.findAllByActivoTrue();
            model.addObject("proveedores", proveedores);
            return model;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            ResponseEntity<PaginatedDataGeneric> res = new ResponseEntity<>(productos,headers, HttpStatusCode.valueOf(200));
            return res;
        }
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("sessions", session);
        List <Proveedor> proveedores=proveedorRepository.findAllByActivoTrue();
        model.addAttribute("proveedores", proveedores);
        return "producto/create";
    }
    @PostMapping()
    public Object save(HttpServletRequest request)throws Exception{
        LinkedHashMap<String, Object> body = RequestParser.parseBody(request);
        Producto producto=productoService.save(body);
        if(producto==null){
            session.setError("Error al crear producto");
            return new RedirectView("/producto/create",true);
        }
        String contentHeader = request.getHeader("content-type");
        if (contentHeader.equals("application/x-www-form-urlencoded")) {
            session.setSuccess("Producto creado con exito");
            return new RedirectView("/producto/edit/" + producto.get_id(), true);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            ResponseEntity<Producto> response = new ResponseEntity<>(producto, headers,
                    HttpStatusCode.valueOf(200));
            return response;
        }
    }
    @GetMapping("/edit/{id}")
    public Object edit(@PathVariable Integer id,Model model){
        Optional<Producto> productoOptional=productoService.get(id);
        if (productoOptional.isPresent()) {
            model.addAttribute("sessions", session);
            Producto producto = productoOptional.get();
            List <Proveedor> proveedores=proveedorRepository.findAllByActivoTrue();
            model.addAttribute("proveedores", proveedores);
            model.addAttribute("producto", producto);
            return "producto/edit";
        } else {
            session.setError("Producto no encontrado");
            return new RedirectView("/producto", true);
        }
    }
    @PutMapping(value = "/{id}")
    public Object update(@PathVariable("id") Integer id, HttpServletRequest request) throws Exception {
        LinkedHashMap<String, Object> body = RequestParser.parseBody(request); 
        Producto producto = productoService.update(id, body);        
        if (producto == null) {
            session.setError("Producto no encontrado para actualizar");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<Producto> response = new ResponseEntity<>(producto, headers,
                HttpStatusCode.valueOf(200));
        session.setSuccess("Producto actualizado con exito");
        return response;

    }
}
