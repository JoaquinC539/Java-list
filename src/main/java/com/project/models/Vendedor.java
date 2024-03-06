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
        this.setUpdated_at(updatedAt);
    }
    public Vendedor(Date updatedAt,Date createdAt){
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="_id")
    private Integer _id;

    @Column(name = "nombre",nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 50)
    private String apellido;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "correo_electronico", nullable = false, length = 50)
    private String correo_electronico;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "created_at")
    private Date created_at;
    @Column(name = "updated_at")
    private Date updated_at;
}
