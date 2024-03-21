package com.project.services.auth;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.models.Producto;
import com.project.models.Proveedor;
import com.project.repository.producto.ProductoRepository;
import com.project.repository.proveedor.ProveedorRepository;
import com.project.utils.PaginatedDataGeneric;
import com.project.utils.PagingHelper;
import com.project.utils.RequestParser;
import com.project.utils.TypeConverter;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class ProductoService {
    
    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    public PaginatedDataGeneric index(HttpServletRequest request){
        String baseSql = "SELECT productos.*," + 
                        " proveedores.nombre AS proveedor_nombre " + 
                        " FROM productos" + 
                        " INNER JOIN proveedores " + 
                        " ON productos.proveedor=proveedores._id " + 
                        " WHERE 1=1";
        LinkedList<Object> values = new LinkedList<Object>();
        HashMap<String, String> params = RequestParser.parseQueryParams(request);
        if (params.containsKey("_id")) {
            baseSql += " AND productos._id=?";
            values.add(Long.parseLong(params.get("_id")));
        }
        if (params.containsKey("nombre")) {
            baseSql += " AND productos.nombre ILIKE ?";
            values.add(params.get("nombre") + "%");
        }
        if(params.containsKey("category")){
            baseSql +=" AND productos.category ILIKE ?";
            values.add(params.get("category")+"%");
        }
        if (params.containsKey("precio")) {
            baseSql += " AND productos.precio BETWEEN ? AND ?";
            values.add(Integer.parseInt(params.get("precio")) * 0.9);
            values.add(Integer.parseInt(params.get("precio")) * 1.1);
        }
        if (params.containsKey("proveedor")){
            baseSql +=" AND productos.proveedor=? ";
            values.add(Integer.parseInt(params.get("proveedor")));
        }

         baseSql += " ORDER BY _id DESC";

        PaginatedDataGeneric data=PagingHelper.fetchPaginatedDataGeneric(baseSql, values, request);
        return data;
    }

    public Producto save(LinkedHashMap<String,Object> body){
        try {
            Producto producto=new Producto();
            producto.setCategory(TypeConverter.convertToString(body.get("category")));
            producto.setDescripcion(TypeConverter.convertToString(body.get("descripcion")));
            producto.setNombre(TypeConverter.convertToString(body.get("nombre")));
            producto.setPrecio(TypeConverter.convertToFloat(body.get("precio")));
            
            @SuppressWarnings("null")
            Optional<Proveedor> proveedor = proveedorRepository.findById(TypeConverter.convertToInteger(body.get("proveedor")));
            producto.setProveedor(proveedor.get());
            
            return productoRepository.save(producto);
        } catch (Exception e) {
            System.out.println("Producto was not created: " + e);
            return null;
        }
    }
    public Optional<Producto> get(int id){
        try {
            Optional<Producto> productoOptional=productoRepository.findById(id);
            return productoOptional;
        } catch (Exception e) {            
            return null;
        }
    }
    public Producto update(int id,LinkedHashMap<String,Object> body){
        try {
            Optional<Producto> productoOptional=productoRepository.findById(id);
            if(!productoOptional.isPresent()){
                return null;
            }
            Producto producto=productoOptional.get();
            producto.setCategory(TypeConverter.convertToString(body.get("category")));
            producto.setDescripcion(TypeConverter.convertToString(body.get("descripcion")));
            producto.setNombre(TypeConverter.convertToString(body.get("nombre")));
            producto.setPrecio(TypeConverter.convertToFloat(body.get("precio")));
            
            @SuppressWarnings("null")
            Optional<Proveedor> proveedor = proveedorRepository.findById(TypeConverter.convertToInteger(body.get("proveedor")));
            producto.setProveedor(proveedor.get());
            
            return productoRepository.save(producto);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    
}
