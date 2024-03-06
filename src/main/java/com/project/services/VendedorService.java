package com.project.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.models.Vendedor;
import com.project.repository.vendedor.VendedorRepository;
import com.project.utils.PaginatedData;
import com.project.utils.PaginatedDataGeneric;
import com.project.utils.PagingHelper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.LinkedList;


@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    
    public PaginatedData<Vendedor> index(HttpServletRequest request){
        String baseSql="SELECT * FROM vendedores where 1=1 order by _id desc";
        LinkedList<Object> values=new LinkedList<Object>();
        PaginatedData<Vendedor> response=PagingHelper.fetchPaginatedDataModel(baseSql, values, request,Vendedor.class);
        return response;
    }
        
        
    
    

    
}
