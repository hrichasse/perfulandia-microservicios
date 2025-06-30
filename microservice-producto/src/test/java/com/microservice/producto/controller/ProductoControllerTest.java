package com.microservice.producto.controller;

import java.util.Arrays;
import java.util.List;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.IProductoService;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductoController.class)  // Usamos WebMvcTest para enfocarnos solo en el controlador
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Inyectado automáticamente por WebMvcTest

    @MockBean  // Simula la inyección del servicio IProductoService
    private IProductoService productoService;

    @Test
    void getById_ReturnsProductoJson() throws Exception {
        // Preparar el producto simulado
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Armani");
        producto.setModelo("Code");

        // Simular la llamada al servicio
        when(productoService.findById(1L)).thenReturn(producto);

        // Realizar la solicitud GET y verificar el resultado
        mockMvc.perform(get("/api/v1/producto/productos/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Armani"))
               .andExpect(jsonPath("$.modelo").value("Code"));
    }

    @Test
    void getAll_ReturnsListJson() throws Exception {
        // Agregar productos simulados
        List<Producto> productos = Arrays.asList(
            new Producto(1L, "Armani", "Code"),
            new Producto(2L, "Paco Rabanne", "Invictus")
        );

        when(productoService.findAll()).thenReturn(productos);

        mockMvc.perform(get("/api/v1/producto/productos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(2))
               .andExpect(jsonPath("$[0].name").value("Armani"))
               .andExpect(jsonPath("$[1].name").value("Paco Rabanne"));
    }

    @Test
    void getById_NotFound() throws Exception {
        when(productoService.findById(anyLong())).thenThrow(new EntityNotFoundException("Producto no encontrado"));

        mockMvc.perform(get("/api/v1/producto/productos/{id}", 42L))
               .andExpect(status().isNotFound());
    }
}
