package com.microservice.venta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.venta.dto.VentaHateoasDTO;
import com.microservice.venta.exception.GlobalExceptionHandler; // Ajusta el nombre si tu clase es diferente
import com.microservice.venta.model.Venta;
import com.microservice.venta.service.HateoasService;
import com.microservice.venta.service.IVentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.persistence.EntityNotFoundException;


import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VentaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IVentaService ventaService;

    @Mock
    private HateoasService hateoasService;

    @InjectMocks
    private VentaController ventaController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(ventaController)
            .setControllerAdvice(new GlobalExceptionHandler()) // Agrega tu handler aquí
            .build();
    }

    @Test
    void getVentaById_ReturnsVentaJson() throws Exception {
        // aqui Creamos una venta de ejemplo para testear
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);
        VentaHateoasDTO dto = new VentaHateoasDTO(1L, null, null, null, 101L);
        // Simulamos una llamada al servicio
        when(ventaService.findById(1L)).thenReturn(venta);
        when(hateoasService.addLinksToVenta(any(Venta.class))).thenReturn(dto);

        // Realizamos la solicitud get y verificamos el resultado
        mockMvc.perform(get("/api/v1/venta/search/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.productoId").value(101L));
    }

    @Test
    void getVentaById_NotFound() throws Exception {
        // Simulamos que el servicio no encuentra la venta
        when(ventaService.findById(42L)).thenThrow(new EntityNotFoundException("Venta con ID 42 no encontrada"));

        // aca se realiza  la solicitud get y verificamos que se devuelve el error 404
        mockMvc.perform(get("/api/v1/venta/search/{id}", 42L))
               .andExpect(status().isNotFound());
    }

    @Test
    void saveVenta_Created() throws Exception {
        Venta venta = new Venta();
        venta.setProductoId(101L);
        Venta savedVenta = new Venta();
        savedVenta.setId(1L);
        savedVenta.setProductoId(101L);
        VentaHateoasDTO dto = new VentaHateoasDTO(1L, null, null, null, 101L);
        when(ventaService.save(any(Venta.class))).thenReturn(savedVenta);
        when(hateoasService.addLinksToVenta(any(Venta.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/venta/create")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.productoId").value(101L));
    }

    @Test
    void findAllVentas_ReturnsList() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        VentaHateoasDTO dto = new VentaHateoasDTO(1L, null, null, null, null);
        List<Venta> ventas = Collections.singletonList(venta);
        List<VentaHateoasDTO> dtos = Collections.singletonList(dto);

        when(ventaService.findAll()).thenReturn(ventas);
        when(hateoasService.addLinksToVentas(anyList())).thenReturn(dtos);

        mockMvc.perform(get("/api/v1/venta/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findByProductoId_ReturnsVentas() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        VentaHateoasDTO dto = new VentaHateoasDTO(1L, null, null, null, 101L);
        List<Venta> ventas = Collections.singletonList(venta);
        List<VentaHateoasDTO> dtos = Collections.singletonList(dto);

        when(ventaService.findByIdProducto(101L)).thenReturn(ventas);
        when(hateoasService.addLinksToVentas(anyList())).thenReturn(dtos);

        mockMvc.perform(get("/api/v1/venta/search-by-producto/{productoId}", 101L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findByProductoId_NotFound() throws Exception {
        when(ventaService.findByIdProducto(999L)).thenThrow(new EntityNotFoundException("No hay ventas para ese producto"));

        mockMvc.perform(get("/api/v1/venta/search-by-producto/{productoId}", 999L))
                .andExpect(status().isNotFound());
    }
}
