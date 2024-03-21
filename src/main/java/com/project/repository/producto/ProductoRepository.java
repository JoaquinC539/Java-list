package com.project.repository.producto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.models.Producto;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
    
}
