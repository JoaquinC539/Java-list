package com.project.repository.proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import com.project.models.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor,Integer>{
    
}
