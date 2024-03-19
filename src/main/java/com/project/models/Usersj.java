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
@Table(name = "usersj")
@Data
public class Usersj {

    public Usersj() {

    }
    public Usersj(Date updated_at){
        this.setUpdated_at(updated_at);
    }
    public Usersj(Date created_at, Date updated_at){
        this.setCreated_at(created_at);
        this.setUpdated_at(updated_at);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    private Integer _id;

    @Column(name="name")
    private String name;

    @Column(name="role")
    private String role;

    @Column(name="password", nullable = false)
    private String password;
    
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "created_at", nullable=true)
    private Date created_at;

    @Column(name = "updated_at", nullable=true)
    private Date updated_at;
}
