package com.microservice.venta.controller;

import com.microservice.venta.model.Venta;
import com.microservice.venta.service.IVentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public ResponseEntity<?> findById(
        @Parameter(description = "ID de la venta a buscar") @PathVariable Long id) {
        return ResponseEntity.ok(iVentaService.findById(id));
    }

    @Operation(summary = "Buscar ventas por ID de producto", description = "Este endpoint devuelve las ventas asociadas a un producto basado en su ID.")
    @GetMapping("/search-by-producto/{productoId}")
    public ResponseEntity<?> findByProductoId(
        @Parameter(description = "ID del producto para buscar ventas asociadas") @PathVariable Long productoId) {
        return ResponseEntity.ok(iVentaService.findByIdProducto(productoId));
    }
}
