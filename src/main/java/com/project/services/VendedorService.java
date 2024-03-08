package com.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.models.Vendedor;
import com.project.repository.vendedor.VendedorRepository;
import com.project.utils.PaginatedData;
import com.project.utils.PagingHelper;
import com.project.utils.RequestParser;
import com.project.utils.TypeConverter;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

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
        if (params.containsKey("activo")) {
            baseSql += " AND activo=?";
            values.add(Boolean.parseBoolean(params.get("activo")));
        }

        baseSql += " ORDER BY _id DESC";

        PaginatedData<Vendedor> response = PagingHelper.fetchPaginatedDataModel(baseSql, values, request,
                Vendedor.class);
        return response;
    }

    public Object index2(HttpServletRequest request){
        HashMap<String, String> params = RequestParser.parseQueryParams(request);
        // List<Vendedor> vendedores=vendedorRepository.findByName("H"+"%");
        List<Vendedor> vendedores=vendedorRepository.findIndex(
            TypeConverter.convertToLong(params.get("_id")),
            TypeConverter.convertToString(params.get("nombre"))!=null ? TypeConverter.convertToString(params.get("nombre"))+"%":null
           
        );
        
        return vendedores;
    }

    public Vendedor save(LinkedHashMap<String, Object> body) {
        try {
            Vendedor vendedor = new Vendedor(new Date(), new Date());
            vendedor.setNombre(TypeConverter.convertToString(body.get("nombre")));
            vendedor.setApellido(TypeConverter.convertToString(body.get("apellido")));
            vendedor.setEdad(TypeConverter.convertToInteger(body.get("edad")));
            vendedor.setCorreo_electronico(TypeConverter.convertToString(body.get("correo_electronico")));
            vendedor.setActivo(TypeConverter.convertToBoolean(body.get("activo")));
            return vendedor;
            // return vendedorRepository.save(vendedor);
        } catch (Exception e) {
            System.out.println("Vendedor was not created: " + e);
            // Vendedor vendedor = new Vendedor();
            return null;
        }
    }
}
