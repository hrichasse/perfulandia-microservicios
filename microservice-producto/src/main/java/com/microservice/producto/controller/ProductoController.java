package com.microservice.producto.controller;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVenta(@RequestBody Producto producto) {                
        productoService.save(producto);
    }
    
    @GetMapping("/all")
    public ResponseEntity<?> findAllProducto(){
        return ResponseEntity.ok(productoService.findAll());
    }
    
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }
    
    @GetMapping("/search-venta/{idProducto}")
    public ResponseEntity<?> findVentasByIdProducto(@PathVariable Long idProducto){
        return ResponseEntity.ok(productoService.findVentasByIdProducto(idProducto));
    }

}
