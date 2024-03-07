package com.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.models.Vendedor;
import com.project.repository.vendedor.VendedorRepository;
import com.project.utils.PaginatedData;
import com.project.utils.PagingHelper;
import com.project.utils.RequestParser;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.LinkedList;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public PaginatedData<Vendedor> index(HttpServletRequest request) {
        String baseSql = "SELECT * FROM vendedores WHERE 1=1 ";
        LinkedList<Object> values = new LinkedList<Object>();
        HashMap<String, String> params = RequestParser.parseQueryParams(request);
        if (params.containsKey("_id")) {
            baseSql += " AND _id=?";
            values.add(Long.parseLong(params.get("_id")));
        }
        if (params.containsKey("nombre")) {
            baseSql += " AND nombre ILIKE ?";
            values.add(params.get("nombre") + "%");
        }
        if (params.containsKey("apellido")) {
            baseSql += " AND apellido ILIKE ?";
            values.add(params.get("apellido") + "%");
        }
        if (params.containsKey("edad")) {
            baseSql += " AND edad BETWEEN ? AND ?";
            values.add(Integer.parseInt(params.get("edad")) * 0.9);
            values.add(Integer.parseInt(params.get("edad")) * 1.1);
        }
        if (params.containsKey("correo_electronico")) {
            baseSql += " AND correo_electronico ILIKE ?";
            values.add(params.get("correo_electronico") + "%");
        }
        if(params.containsKey("activo")){
            baseSql +=" AND activo=?";
            values.add(Boolean.parseBoolean(params.get("activo")));
        }

        baseSql += " ORDER BY _id DESC";

        PaginatedData<Vendedor> response = PagingHelper.fetchPaginatedDataModel(baseSql, values, request,
                Vendedor.class);
        return response;
    }

    
}
