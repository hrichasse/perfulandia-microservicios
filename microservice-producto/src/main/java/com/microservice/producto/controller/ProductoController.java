package com.microservice.producto.controller;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/producto")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Operation(summary = "Crear un nuevo producto", description = "Este endpoint permite crear un nuevo producto en la base de datos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Producto> saveProducto(@Valid @RequestBody Producto producto) {                
        Producto savedProducto = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }

    @Operation(summary = "Obtener todos los productos", description = "Este endpoint devuelve una lista de todos los productos registrados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
        @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Producto>> findAllProducto(){
        List<Producto> productos = productoService.findAll();
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar producto por ID", description = "Este endpoint devuelve un producto específico basado en el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/search/{id}")
    public ResponseEntity<Producto> findById(
        @Parameter(description = "ID del producto") @PathVariable Long id) {
        Producto producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
    }

    @Operation(summary = "Actualizar producto", description = "Este endpoint permite actualizar un producto existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Producto> updateProducto(
        @Parameter(description = "ID del producto") @PathVariable Long id,
        @Valid @RequestBody Producto producto) {
        producto.setId(id);
        Producto updatedProducto = productoService.save(producto);
        return ResponseEntity.ok(updatedProducto);
    }

    @Operation(summary = "Eliminar producto", description = "Este endpoint permite eliminar un producto por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProducto(
        @Parameter(description = "ID del producto") @PathVariable Long id) {
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar ventas por ID de producto", description = "Este endpoint devuelve las ventas asociadas a un producto basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ventas encontradas"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/search-venta/{idProducto}")
    public ResponseEntity<?> findVentasByIdProducto(
        @Parameter(description = "ID del producto para obtener las ventas") @PathVariable Long idProducto) {
        return ResponseEntity.ok(productoService.findVentasByIdProducto(idProducto));
    }

    // Manejador para la excepción EntityNotFoundException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
