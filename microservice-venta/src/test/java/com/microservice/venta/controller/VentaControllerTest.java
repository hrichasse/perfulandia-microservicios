package com.microservice.venta.controller;

import com.microservice.venta.model.Venta;
import com.microservice.venta.service.IVentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.persistence.EntityNotFoundException;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VentaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IVentaService ventaService;

    @InjectMocks
    private VentaController ventaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
    }

    @Test
    void getVentaById_ReturnsVentaJson() throws Exception {
        // Crear una venta de ejemplo
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);

        // Simular la llamada al servicio
        when(ventaService.findById(1L)).thenReturn(venta);

        // Realizar la solicitud GET y verificar el resultado
        mockMvc.perform(get("/api/v1/venta/search/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.productoId").value(101L));
    }

    @Test
    void getVentaById_NotFound() throws Exception {
        // Simular que el servicio no encuentra la venta
        when(ventaService.findById(42L)).thenThrow(new EntityNotFoundException("Venta con ID 42 no encontrada"));

        // Realizar la solicitud GET y verificar que se devuelve el error 404
        mockMvc.perform(get("/api/v1/venta/search/{id}", 42L))
               .andExpect(status().isNotFound());
    }

    
}
