package com.microservice.venta.controller;

import com.microservice.venta.model.Venta;
import com.microservice.venta.service.IVentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Usamos MockMvc para simular las peticiones HTTP

    @MockBean
    private IVentaService ventaService;  // Simulamos el servicio

    @InjectMocks
    private VentaController ventaController;  // El controlador que estamos probando

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc con el controlador real
        mockMvc = MockMvcBuilders.standaloneSetup(ventaController).build();
    }

    @Test
    void testCreateVenta() throws Exception {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);

        // Simula la respuesta del servicio cuando se guarda una venta
        doNothing().when(ventaService).save(any(Venta.class));

        // Realizamos una solicitud POST simulada
        mockMvc.perform(post("/api/v1/venta/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": 1, \"productoId\": 101, \"name\": \"Venta Test\", \"idventa\": \"V1234\"}"))
                .andExpect(status().isCreated())  // Verifica que la respuesta sea HTTP 201 (Created)
                .andExpect(content().string(""));  // Verifica que el cuerpo de la respuesta esté vacío

        verify(ventaService, times(1)).save(any(Venta.class));  // Verifica que el servicio 'save' fue llamado
    }

    @Test
    void testFindAllVentas() throws Exception {
        // Simula una lista de ventas
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);

        when(ventaService.findAll()).thenReturn(Arrays.asList(venta));

        // Realizamos una solicitud GET simulada
        mockMvc.perform(get("/api/v1/venta/all"))
                .andExpect(status().isOk())  // Verifica que la respuesta sea HTTP 200 (OK)
                .andExpect(jsonPath("$[0].id").value(1L))  // Verifica que la primera venta tiene el ID 1
                .andExpect(jsonPath("$[0].productoId").value(101L));  // Verifica que el productoId sea 101

        verify(ventaService, times(1)).findAll();  // Verifica que el servicio 'findAll' fue llamado
    }

    @Test
    void testFindByIdVenta() throws Exception {
        // Simula una venta que debe ser devuelta
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);

        when(ventaService.findById(1L)).thenReturn(venta);

        // Realizamos una solicitud GET simulada para obtener una venta por ID
        mockMvc.perform(get("/api/v1/venta/search/1"))
                .andExpect(status().isOk())  // Verifica que la respuesta sea HTTP 200 (OK)
                .andExpect(jsonPath("$.id").value(1L))  // Verifica que el ID de la venta sea 1
                .andExpect(jsonPath("$.productoId").value(101L));  // Verifica que el productoId sea 101

        verify(ventaService, times(1)).findById(1L);  // Verifica que el servicio 'findById' fue llamado
    }

    @Test
    void testFindByIdProducto() throws Exception {
        // Simula una venta asociada a un productoId
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);

        when(ventaService.findByIdProducto(101L)).thenReturn(Arrays.asList(venta));

        // Realizamos una solicitud GET simulada para obtener ventas por productoId
        mockMvc.perform(get("/api/v1/venta/search-by-producto/101"))
                .andExpect(status().isOk())  // Verifica que la respuesta sea HTTP 200 (OK)
                .andExpect(jsonPath("$[0].id").value(1L))  // Verifica que la primera venta tiene el ID 1
                .andExpect(jsonPath("$[0].productoId").value(101L));  // Verifica que el productoId sea 101

        verify(ventaService, times(1)).findByIdProducto(101L);  // Verifica que el servicio 'findByIdProducto' fue llamado
    }

    @Test
    void testFindByIdProductoNotFound() throws Exception {
        when(ventaService.findByIdProducto(101L)).thenReturn(List.of());

        // Realizamos una solicitud GET simulada para obtener ventas por productoId que no existen
        mockMvc.perform(get("/api/v1/venta/search-by-producto/101"))
                .andExpect(status().isNotFound())  // Verifica que la respuesta sea HTTP 404 (Not Found)
                .andExpect(content().string("No se encontraron ventas para el producto con ID 101"));  // Verifica el mensaje de error

        verify(ventaService, times(1)).findByIdProducto(101L);  // Verifica que el servicio 'findByIdProducto' fue llamado
    }
}
