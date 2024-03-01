package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.project.models.Vendedor;

// @Repository
public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
    
}
