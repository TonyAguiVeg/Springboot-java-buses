package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Bus;
import com.example.demo.enums.Tipo;
import com.example.demo.repository.BusRepository;


@Service
public class BusService {
	
	@Autowired
	private  BusRepository busrepository;
	
	
	 public ResponseEntity<Map<String, Object>> listarBusesPaginados(int pagina, int tamano) {
	        Map<String, Object> response = new HashMap<>();
	        Pageable pageable = PageRequest.of(pagina, tamano);
	        Page<Bus> pageBuses = busrepository.findAll(pageable);
	        
	        if(pageBuses.isEmpty()) {
	            response.put("mensaje", "No existen buses registrados");
	            response.put("fecha", new Date());
	            response.put("status", HttpStatus.NO_CONTENT.value());
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	        }
	        
	        response.put("mensaje", "Lista de buses obtenida correctamente");
	        response.put("fecha", new Date());
	        response.put("status", HttpStatus.OK.value());
	        response.put("buses", pageBuses.getContent());
	        response.put("paginaActual", pageBuses.getNumber());
	        response.put("totalBuses", pageBuses.getTotalElements());
	        response.put("totalPaginas", pageBuses.getTotalPages());
	        
	        return ResponseEntity.ok(response);
	    }
	
	
	public ResponseEntity<Map<String, Object>> obtenerBusPorId(Long id) {
        Map<String, Object> response = new HashMap<>();
        Bus bus = busrepository.findById(id).orElse(null);
        
        if(bus == null) {
            response.put("mensaje", "No se encontró el bus con ID: " + id);
            response.put("fecha", new Date());
            response.put("status", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else {
            response.put("mensaje", "Bus encontrado");
            response.put("fecha", new Date());
            response.put("status", HttpStatus.OK);
            response.put("bus", bus);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }
	
	
	public ResponseEntity<Map<String, Object>> crearBus(Bus bus) {
        Map<String, Object> response = new HashMap<>();
        
        if(bus.getNumBus() == null && bus.getTipo() != null) {
            bus.setNumBus(GenerarNumBus(bus.getTipo()));
        }
        
        Bus busGuardado = busrepository.save(bus);
        
        response.put("mensaje", "Bus creado exitosamente");
        response.put("fecha", new Date());
        response.put("status", HttpStatus.CREATED);
        response.put("bus", busGuardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	
	public String GenerarNumBus (Tipo tipo) {
		int conteoActual= busrepository.countByTipo(tipo);
		return tipo.toString().toUpperCase().substring(0,1) +"-"+(conteoActual+1);
	}

	
	 public boolean validarEstado(String estado) {
	        return estado != null && (estado.equalsIgnoreCase("Activo") || 
	                                 estado.equalsIgnoreCase("Inactivo"));
	    }
}
