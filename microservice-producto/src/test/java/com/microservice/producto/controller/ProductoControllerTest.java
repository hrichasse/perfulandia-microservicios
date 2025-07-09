package com.microservice.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.producto.dto.ProductoHateoasDTO;
import com.microservice.producto.http.response.VentaByProductoResponse;
import com.microservice.producto.model.Producto;
import com.microservice.producto.service.HateoasService;
import com.microservice.producto.service.IProductoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IProductoService productoService;

    @Mock
    private HateoasService hateoasService;

    @InjectMocks
    private ProductoController productoController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController)
                .setControllerAdvice(productoController) // Para manejar excepciones
                .build();
    }

    @Test
    void saveProducto_Created() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        ProductoHateoasDTO dto = new ProductoHateoasDTO(1L, null, null);
        when(productoService.save(any(Producto.class))).thenReturn(producto);
        when(hateoasService.addLinksToProducto(any(Producto.class))).thenReturn(dto);

        mockMvc.perform(post("/api/v1/producto/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void findAllProducto_ReturnsList() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        ProductoHateoasDTO dto = new ProductoHateoasDTO(1L, null, null);
        List<Producto> productos = Collections.singletonList(producto);
        List<ProductoHateoasDTO> dtos = Collections.singletonList(dto);

        when(productoService.findAll()).thenReturn(productos);
        when(hateoasService.addLinksToProductos(anyList())).thenReturn(dtos);

        mockMvc.perform(get("/api/v1/producto/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void findAllProducto_NoContent() throws Exception {
        when(productoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/producto/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    void findById_ReturnsProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        ProductoHateoasDTO dto = new ProductoHateoasDTO(1L, null, null);
        when(productoService.findById(1L)).thenReturn(producto);
        when(hateoasService.addLinksToProducto(any(Producto.class))).thenReturn(dto);

        mockMvc.perform(get("/api/v1/producto/search/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void updateProducto_ReturnsUpdatedProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        ProductoHateoasDTO dto = new ProductoHateoasDTO(1L, null, null);
        when(productoService.save(any(Producto.class))).thenReturn(producto);
        when(hateoasService.addLinksToProducto(any(Producto.class))).thenReturn(dto);

        mockMvc.perform(put("/api/v1/producto/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void deleteProducto_NoContent() throws Exception {
        doNothing().when(productoService).deleteById(1L);

        mockMvc.perform(delete("/api/v1/producto/delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void findVentasByIdProducto_ReturnsVentas() throws Exception {
        VentaByProductoResponse ventasResponse = new VentaByProductoResponse(); // Usa tu DTO real
        when(productoService.findVentasByIdProducto(1L)).thenReturn(ventasResponse);

        mockMvc.perform(get("/api/v1/producto/search-venta/{idProducto}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void findVentasByIdProducto_NotFound() throws Exception {
        when(productoService.findVentasByIdProducto(99L))
                .thenThrow(new EntityNotFoundException("No encontrado"));

        mockMvc.perform(get("/api/v1/producto/search-venta/{idProducto}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void handleEntityNotFoundException_ReturnsNotFound() {
        EntityNotFoundException ex = new EntityNotFoundException("No encontrado");
        ProductoController controller = new ProductoController();
        ResponseEntity<String> response = controller.handleEntityNotFoundException(ex);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No encontrado", response.getBody());
    }
}
