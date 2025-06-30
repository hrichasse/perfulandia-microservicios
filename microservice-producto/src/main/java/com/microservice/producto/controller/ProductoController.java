package com.microservice.producto.controller;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.IProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Crear un nuevo producto", description = "Este endpoint permite crear un nuevo producto en la base de datos.")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVenta(@RequestBody Producto producto) {                
        productoService.save(producto);
    }
    
    @Operation(summary = "Obtener todos los productos", description = "Este endpoint devuelve una lista de todos los productos registrados.")
    @GetMapping("/all")
    public ResponseEntity<?> findAllProducto(){
        return ResponseEntity.ok(productoService.findAll());
    }
    
    @Operation(summary = "Buscar producto por ID", description = "Este endpoint devuelve un producto específico basado en el ID proporcionado.")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(
        @Parameter(description = "ID del producto") @PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }
    
    @Operation(summary = "Buscar ventas por ID de producto", description = "Este endpoint devuelve las ventas asociadas a un producto basado en su ID.")
    @GetMapping("/search-venta/{idProducto}")
    public ResponseEntity<?> findVentasByIdProducto(
        @Parameter(description = "ID del producto para obtener las ventas") @PathVariable Long idProducto) {
        return ResponseEntity.ok(productoService.findVentasByIdProducto(idProducto));
    }
}
