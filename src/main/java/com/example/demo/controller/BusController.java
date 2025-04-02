package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.BusService;

@RestController
@RequestMapping("/bus")
public class BusController {

	@Autowired
    private BusService busService;

  
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarBuses(
            												@RequestParam(defaultValue = "0") int pagina,
            												@RequestParam(defaultValue = "10") int tamano) {
        return busService.listarBusesPaginados(pagina, tamano);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerBusPorId(@PathVariable Long id) {
        return busService.obtenerBusPorId(id);
    }
	
	
}
