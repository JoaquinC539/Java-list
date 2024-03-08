package com.project.repository.vendedor;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.models.Vendedor;

// @Repository
public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
    @Query(value = "SELECT * FROM vendedores WHERE nombre ILIKE ?1", nativeQuery = true)
    List<Vendedor> findByName (String name);
    @Query(value = "SELECT * FROM vendedores WHERE 1=1 "+
    " AND (:id IS NULL OR vendedores._id=:id)"+
    " AND (:nombre IS NULL OR vendedores.nombre ILIKE :nombre)"+
    " ORDER BY vendedores._id DESC"
    ,
    nativeQuery = true)
    List<Vendedor> findIndex(
        @Param("id") Long id,
        @Param("nombre") String nombre
        );
}

