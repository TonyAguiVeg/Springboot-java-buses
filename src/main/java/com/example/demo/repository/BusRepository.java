package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Bus;
import com.example.demo.enums.Tipo;

@Repository
public interface BusRepository extends JpaRepository <Bus,Long>{

	int countByTipo(Tipo tipo);
}
