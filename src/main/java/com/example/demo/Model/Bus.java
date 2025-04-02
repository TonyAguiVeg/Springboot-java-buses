package com.example.demo.Model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.enums.Tipo;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.JoinColumn;


@Data

@Entity
@Table(name="tb_bus")
public class Bus {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bus")
	private long idBus;
	
	@Column(name="num_bus",length=6, nullable=true,unique=true)
	private String NumBus;
	
	@Column(name="placa", length=7)
	private String placa;
	
	@Column(name="caracteristicas", length=250)
	private String caracteristicas;
	
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "id_marca")
	private Marca marca;
	
	@Column(name="estado")
	private String estado;
	
	@CreatedDate
	@Column(name="fecha_creacion", updatable=false)
	private LocalDate fechaCreacion;
	
	@Column(name="tipo")
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
}


