package com.project.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proveedores")
@Data
public class Proveedor {

    public Proveedor(){}
    public Proveedor(Date updatedAt){
        this.setUpdated_at(updatedAt);
    }
    public Proveedor(Date updatedAt,Date createdAt){
        this.setUpdated_at(updatedAt);
        this.setCreated_at(createdAt);
    }

    public void update(){
        this.setUpdated_at(new Date());
    }
    public void create(){
        this.setUpdated_at(new Date());
        this.setCreated_at(new Date());
    }

    @Id
    @GeneratedValue
    @Column(name = "_id")
    private Integer _id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "contacto", nullable = false, length = 50)
    private String contacto;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "direccion", nullable = false, length = 50)
    private String direccion;

    @Column(name = "activo")
    private Boolean activo;
    
    @Column(name = "created_at", nullable = true)
    private Date created_at;
    @Column(name = "updated_at", nullable = true)
    private Date updated_at;
}
