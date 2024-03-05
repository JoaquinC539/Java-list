package com.project.repository.vendedor;





import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;


import com.project.models.Vendedor;

// @Repository
public interface VendedorRepository extends JpaRepository<Vendedor,Integer> {
    // List<Vendedor> findAllByOderByIdDescAndIdGreaterThan(Long id); 
    // List<Vendedor> findByIdGreaterThanOrderByIdDesc(Long id); 
    // @Query(value = "SELECT * FROM public.vendedores WHERE nombre ILIKE CONCAT('%':nombre,'%') ORDER BY _id DESC",nativeQuery = true)
    // List<Vendedor> findAllByName(@Param("nombre") String nombre); 
}
