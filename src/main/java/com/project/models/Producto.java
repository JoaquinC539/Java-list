package com.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {
    public Producto(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    private Integer _id;

    @Column(name="nombre",nullable = false)
    private String nombre;

    @Column(name = "precio",nullable = false)
    private Float precio;

    @Column(name = "category",nullable = false)
    private String category;

    @Column(name= "descripcion",nullable = false,length = 1000)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "proveedor",referencedColumnName = "_id",nullable = false)
    private Proveedor proveedor;
    
}
