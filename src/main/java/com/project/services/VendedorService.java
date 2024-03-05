package com.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.models.Vendedor;
import com.project.repository.vendedor.VendedorRepository;
import com.project.utils.Querier;

import java.util.LinkedList;
import java.util.List;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> index(){
        
        Sort sort=Sort.by(Sort.Order.desc("_id"));
        Pageable pageable=PageRequest.of(0,10,sort);
        Page<Vendedor> vPage=vendedorRepository.findAll(pageable);  
        return vPage.getContent();
    }
        
    public Object index2(){
        // String sql = "SELECT * FROM vendedores WHERE vendedores.nombre=?";
        String sql = "SELECT * FROM vendedores WHERE vendedores.nombre ILIKE ? AND vendedores._id=?";
        LinkedList<Object> values=new LinkedList<>();
        values.add("H%");
        values.add(Long.parseLong("20"));
        Object data=Querier.query(sql, values);
        
        return data;

    }

    
}
