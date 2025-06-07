package com.microservice.venta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.venta.model.Venta;
import com.microservice.venta.service.IVentaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;






@RestController
@RequestMapping("/api/v1/venta")
public class VentaController {


    @Autowired
    private IVentaService iVentaService;

    
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVentas(@RequestBody Venta venta){
        iVentaService.save(venta);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllStudents(){
        return ResponseEntity.ok(iVentaService.findAll());
    }
    

    @GetMapping("/search/{id}")    
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(iVentaService.findById(id));
    }

    //localhost:8090/api/v1/venta/search-by-producto/1
    @GetMapping("/search-by-cour/{productoId}")
    public ResponseEntity<?> findByIdProducto(@PathVariable Long productoId){
         System.out.println("-------------------------------------------------------------------"+ productoId);
         return ResponseEntity.ok(iVentaService.findByIdProducto(productoId));
    }

}
