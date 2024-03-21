package com.project.services;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.models.Proveedor;
import com.project.repository.proveedor.ProveedorRepository;
import com.project.utils.PaginatedData;
import com.project.utils.PagingHelper;
import com.project.utils.RequestParser;
import com.project.utils.TypeConverter;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public PaginatedData<Proveedor> index(HttpServletRequest request) {
        String baseSql = "SELECT * FROM proveedores WHERE 1=1 ";
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
        if (params.containsKey("contacto")) {
            baseSql += " AND contacto ILIKE ?";
            values.add(params.get("contacto") + "%");
        }
        if (params.containsKey("email")) {
            baseSql += " AND email ILIKE ?";
            values.add(params.get("email") + "%");
        }
        if (params.containsKey("activo")) {
            baseSql += " AND activo=?";
            values.add(Boolean.parseBoolean(params.get("activo")));
        }

        baseSql += " ORDER BY _id DESC";

        PaginatedData<Proveedor> data = PagingHelper.fetchPaginatedDataModel (baseSql, values, request, Proveedor.class);
        
        return data;
    }

    public Proveedor save(LinkedHashMap<String, Object> body) {
        try {
            Proveedor proveedor = new Proveedor(new Date(), new Date());
            proveedor.setNombre(TypeConverter.convertToString(body.get("nombre")));
            proveedor.setContacto(TypeConverter.convertToString(body.get("contacto")));
            proveedor.setEmail(TypeConverter.convertToString(body.get("email")));
            proveedor.setDireccion(TypeConverter.convertToString(body.get("direccion")));
            proveedor.setActivo(TypeConverter.convertToBoolean(body.get("activo")));
            return proveedorRepository.save(proveedor);
        } catch (Exception e) {
            System.out.println("Proveedor was not created: " + e);
            return null;
        }
    }

    public Optional<Proveedor> get(int id) {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findById(id);
            return proveedor;
        } catch (Exception e) {
            return null;
        }
    }

    public Proveedor update(int id, LinkedHashMap<String, Object> body) {
        try {
            Optional<Proveedor> proveedorOptional = proveedorRepository.findById(id);
            if (!proveedorOptional.isPresent()) {
                return null;
            } else {
                Proveedor proveedor = proveedorOptional.get();
                proveedor.setNombre(TypeConverter.convertToString(body.get("nombre")));
                proveedor.setContacto(TypeConverter.convertToString(body.get("contacto")));
                proveedor.setEmail(TypeConverter.convertToString(body.get("email")));
                proveedor.setDireccion(TypeConverter.convertToString(body.get("direccion")));
                proveedor.setActivo(TypeConverter.convertToBoolean(body.get("activo")));
                proveedor.setUpdated_at(new Date());
                Proveedor proveedorUpdate = proveedorRepository.save(proveedor);
                return proveedorUpdate;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
