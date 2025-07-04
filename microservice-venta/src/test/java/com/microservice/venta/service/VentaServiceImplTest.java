package com.microservice.venta.service;

import com.microservice.venta.model.Venta;
import com.microservice.venta.repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaServiceImpl ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ReturnsList() {
        List<Venta> ventas = Arrays.asList(new Venta(), new Venta());
        when(ventaRepository.findAll()).thenReturn(ventas);

        List<Venta> result = ventaService.findAll();

        assertEquals(2, result.size());
        verify(ventaRepository).findAll();
    }

    @Test
    void findById_ReturnsVenta() {
        Venta venta = new Venta();
        venta.setId(1L);
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Venta result = ventaService.findById(1L);

        assertEquals(1L, result.getId());
        verify(ventaRepository).findById(1L);
    }

    @Test
    void findById_ThrowsException() {
        when(ventaRepository.findById(2L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> ventaService.findById(2L));
        assertTrue(ex.getMessage().contains("Venta con ID 2 no encontrada"));
    }

    @Test
    void save_CallsRepository() {
        Venta venta = new Venta();
        ventaService.save(venta);
        verify(ventaRepository).save(venta);
    }

    @Test
    void findByIdProducto_ReturnsVentas() {
        List<Venta> ventas = Arrays.asList(new Venta(), new Venta());
        when(ventaRepository.findAllByProductoId(1L)).thenReturn(ventas);

        List<Venta> result = ventaService.findByIdProducto(1L);

        assertEquals(2, result.size());
        verify(ventaRepository).findAllByProductoId(1L);
    }

    @Test
    void findByIdProducto_ThrowsExceptionIfEmpty() {
        when(ventaRepository.findAllByProductoId(2L)).thenReturn(Collections.emptyList());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> ventaService.findByIdProducto(2L));
        assertTrue(ex.getMessage().contains("No se encontraron ventas para el producto con ID 2"));
    }

    @Test
    void findByIdProducto_ThrowsExceptionIfNull() {
        when(ventaRepository.findAllByProductoId(3L)).thenReturn(null);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> ventaService.findByIdProducto(3L));
        assertTrue(ex.getMessage().contains("No se encontraron ventas para el producto con ID 3"));
    }

    @Test
    void findByIdVenta_ThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> ventaService.findByIdVenta(1L));
    }
}