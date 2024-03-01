package com.project.models;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Table(name = "vendedores")
@Data
public class Vendedor {
    public Vendedor(){}
    public Vendedor(Date updatedAt){
        this.setUpdatedAt(updatedAt);
    }
    public Vendedor(Date updatedAt,Date createdAt){
        this.setUpdatedAt(updatedAt);
        this.setCreatedAt(createdAt);
    }

    public void update(){
        this.setUpdatedAt(new Date());
    }
    public void create(){
        this.setUpdatedAt(new Date());
        this.setCreatedAt(new Date());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name = "nombre",nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "correo_electronico", nullable = false, length = 50)
    private String correoElectronico;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
}
