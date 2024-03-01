package com.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.repository.VendedorRepository;
import com.project.models.Vendedor;
import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> index(){
        return vendedorRepository.findAll();
    }
    
}
