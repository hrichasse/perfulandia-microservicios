package com.microservice.producto.service;

import com.microservice.producto.client.VentaClient;
import com.microservice.producto.dto.VentaDTO;
import com.microservice.producto.http.response.VentaByProductoResponse;
import com.microservice.producto.model.Producto;
import com.microservice.producto.repository.IProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {

    @Mock
    private IProductoRepository productoRepository;

    @Mock
    private VentaClient ventaClient;

    @InjectMocks
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsList() {
        List<Producto> productos = Arrays.asList(new Producto(), new Producto());
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> result = productoService.findAll();

        assertEquals(2, result.size());
        verify(productoRepository).findAll();
    }

    @Test
    void findById_ReturnsProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto result = productoService.findById(1L);

        assertEquals(1L, result.getId());
        verify(productoRepository).findById(1L);
    }

    @Test
    void findById_ThrowsException() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> productoService.findById(2L));
        assertTrue(ex.getMessage().contains("Producto con ID 2 no encontrado"));
    }

    @Test
    void save_ReturnsProducto() {
        Producto producto = new Producto();
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto result = productoService.save(producto);

        assertEquals(producto, result);
        verify(productoRepository).save(producto);
    }

    @Test
    void deleteById_DeletesProducto() {
        when(productoRepository.existsById(1L)).thenReturn(true);

        productoService.deleteById(1L);

        verify(productoRepository).deleteById(1L);
    }

    @Test
    void deleteById_ThrowsException() {
        when(productoRepository.existsById(2L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> productoService.deleteById(2L));
        assertTrue(ex.getMessage().contains("Producto con ID 2 no encontrado"));
    }

    @Test
    void findVentasByIdProducto_ReturnsResponse() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("TestName");
        producto.setModelo("TestModelo");

        List<VentaDTO> ventas = Arrays.asList(new VentaDTO(), new VentaDTO());

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(ventaClient.findAllVentaByProducto(1L)).thenReturn(ventas);

        VentaByProductoResponse response = productoService.findVentasByIdProducto(1L);

        assertEquals("TestName", response.getProductoName());
        assertEquals("TestModelo", response.getModelo());
        assertEquals(2, response.getVentaDTOList().size());
    }

    @Test
    void findVentasByIdProducto_ThrowsException() {
        when(productoRepository.findById(3L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> productoService.findVentasByIdProducto(3L));
        assertTrue(ex.getMessage().contains("Producto con ID 3 no encontrado"));
    }
}