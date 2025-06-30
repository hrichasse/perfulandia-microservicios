package com.microservice.venta.controller;

import com.microservice.venta.model.Venta;
import com.microservice.venta.service.IVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venta")
public class VentaController {

    @Autowired
    private IVentaService iVentaService;

    @Operation(summary = "Crear una nueva venta", description = "Este endpoint permite crear una nueva venta.")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveVenta(@RequestBody Venta venta) {
        iVentaService.save(venta);
    }

    @Operation(summary = "Obtener todas las ventas", description = "Este endpoint devuelve una lista de todas las ventas registradas.")
    @GetMapping("/all")
    public ResponseEntity<?> findAllVentas() {
        return ResponseEntity.ok(iVentaService.findAll());
    }

    @Operation(summary = "Buscar venta por ID", description = "Este endpoint devuelve una venta específica basado en el ID proporcionado.")
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Venta venta = iVentaService.findById(id);  // Llamada al servicio
            return ResponseEntity.ok(venta);  // Si la venta se encuentra, retorna un 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // Si no se encuentra, devuelve 404 con el mensaje de la excepción
        }
    }

    @Operation(summary = "Buscar ventas por ID de producto", description = "Este endpoint devuelve las ventas asociadas a un producto basado en su ID.")
    @GetMapping("/search-by-producto/{productoId}")
    public ResponseEntity<?> findByProductoId(@PathVariable Long productoId) {
        try {
            List<Venta> ventas = iVentaService.findByIdProducto(productoId);  // Llamada al servicio para obtener ventas
            return ResponseEntity.ok(ventas);  // Si se encuentran ventas, retorna 200 OK
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());  // Si no se encuentran ventas, retorna 404
        }
    }
}
