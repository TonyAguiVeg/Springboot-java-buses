package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tb_marca")
public class Marca {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_marca")
    private long idmarca;
    
    @Column(name="nom_marca")
    private String nomMarca;
    
    @Column(name="modelo", length=30)
    private String modelo;
    
    @Column(name="año")
    private int año;
    
    @OneToMany(mappedBy = "marca")
    @JsonManagedReference
    private List<Bus> buses = new ArrayList<>();
}
