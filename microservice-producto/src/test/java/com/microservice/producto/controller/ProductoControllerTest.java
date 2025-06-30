package com.microservice.producto.controller;

import com.microservice.producto.controller.ProductoController;
import com.microservice.producto.model.Producto;
import com.microservice.producto.service.IProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IProductoService service;

    @Test
    @DisplayName("GET /productos → lista JSON de productos")
    void getAll_ReturnsListJson() throws Exception {
        // dado
        Producto p1 = new Producto(1L, "Armani", "Code");
        Producto p2 = new Producto(2L, "Paco Rabbane", "Fruit");
        when(service.findAll()).thenReturn(Arrays.asList(p1, p2));

        mvc.perform(get("/productos")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.length()").value(2))
           .andExpect(jsonPath("$[0].id").value(1))
           .andExpect(jsonPath("$[0].name").value("Armani"))
           .andExpect(jsonPath("$[1].id").value(2))
           .andExpect(jsonPath("$[1].modelo").value("Fruit"));
    }

    @Test
    @DisplayName("GET /productos/{id} → producto existente JSON")
    void getById_ReturnsProductoJson() throws Exception {
        Producto p = new Producto(1L, "Antonio Banderas", "Love");
        when(service.findById(1L)).thenReturn(p);
        mvc.perform(get("/productos/1")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1))
           .andExpect(jsonPath("$.name").value("Antonio Banderas"))
           .andExpect(jsonPath("$.modelo").value("Love"));
    }

    @Test
    @DisplayName("GET /productos/{id} → 404 si no existe")
    void getById_NotFound() throws Exception {
        when(service.findById(anyLong()))
            .thenThrow(new jakarta.persistence.EntityNotFoundException("No existe"));

        mvc.perform(get("/productos/99")
                .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }
}