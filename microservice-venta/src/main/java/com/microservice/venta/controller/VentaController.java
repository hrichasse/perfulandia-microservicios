package com.microservice.venta.controller;

import com.microservice.venta.dto.VentaHateoasDTO;
import com.microservice.venta.model.Venta;
import com.microservice.venta.service.HateoasService;
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
    
    @Autowired
    private HateoasService hateoasService;

    @Operation(summary = "Crear una nueva venta", description = "Este endpoint permite crear una nueva venta.")
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<VentaHateoasDTO> saveVenta(@RequestBody Venta venta) {
        Venta savedVenta = iVentaService.save(venta);
        VentaHateoasDTO dto = hateoasService.addLinksToVenta(savedVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @Operation(summary = "Obtener todas las ventas", description = "Este endpoint devuelve una lista de todas las ventas registradas.")
    @GetMapping("/all")
    public ResponseEntity<List<VentaHateoasDTO>> findAllVentas() {
        List<Venta> ventas = iVentaService.findAll();
        List<VentaHateoasDTO> dtos = hateoasService.addLinksToVentas(ventas);
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Buscar venta por ID", description = "Este endpoint devuelve una venta específica basado en el ID proporcionado.")
    @GetMapping("/search/{id}")
    public ResponseEntity<VentaHateoasDTO> findById(@PathVariable Long id) {
        try {
            Venta venta = iVentaService.findById(id);
            VentaHateoasDTO dto = hateoasService.addLinksToVenta(venta);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Buscar ventas por ID de producto", description = "Este endpoint devuelve las ventas asociadas a un producto basado en su ID.")
    @GetMapping("/search-by-producto/{productoId}")
    public ResponseEntity<List<VentaHateoasDTO>> findByProductoId(@PathVariable Long productoId) {
        try {
            List<Venta> ventas = iVentaService.findByIdProducto(productoId);
            List<VentaHateoasDTO> dtos = hateoasService.addLinksToVentas(ventas);
            return ResponseEntity.ok(dtos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
