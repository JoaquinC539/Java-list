package com.project.repository.proveedor;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.models.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor,Integer>{
    List<Proveedor> findAllByActivoTrue();
}
