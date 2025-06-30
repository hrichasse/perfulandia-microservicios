package com.microservice.venta.service;

import com.microservice.venta.model.Venta;
import com.microservice.venta.repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository repo;  // Repositorio simulado

    @InjectMocks
    private VentaServiceImpl service;  // El servicio real bajo prueba

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void findByIdExisting() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);  // Usamos productoId
        when(repo.findById(1L)).thenReturn(Optional.of(venta));

        Venta result = service.findById(1L);

        assertNotNull(result);
        assertEquals(101L, result.getProductoId());  // Verificamos el productoId
        verify(repo).findById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        // Verifica que se lanza la excepción con el mensaje adecuado
        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class,
                () -> service.findById(42L));
        
        assertEquals("Venta con ID 42 no encontrada", thrown.getMessage());  // Comprobamos el mensaje de la excepción
        verify(repo).findById(42L);
    }

    @Test
    void findByIdProductoExisting() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(101L);  // Usamos productoId
        when(repo.findAllByProductoId(101L)).thenReturn(List.of(venta));

        List<Venta> result = service.findByIdProducto(101L);

        assertNotNull(result);
        assertEquals(1, result.size());  // Verificamos que haya una venta asociada
        assertEquals(101L, result.get(0).getProductoId());
        verify(repo).findAllByProductoId(101L);
    }

@Test
void findByIdProductoNotFound() {
    // Simulamos que el repositorio devuelve una lista vacía
    when(repo.findAllByProductoId(anyLong())).thenReturn(new ArrayList<>());  // Simulamos que no se encuentran ventas

    // Verificamos que la excepción EntityNotFoundException sea lanzada cuando llamamos al servicio
    EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, 
        () -> service.findByIdProducto(42L));  // Aquí 42L es el id del producto

    // Verificamos el mensaje de la excepción
    assertEquals("No se encontraron ventas para el producto con ID 42", thrown.getMessage());

    // Verificamos que el repositorio fue llamado con el id del producto
    verify(repo).findAllByProductoId(42L);  // Usamos el nombre correcto de la variable mockeada
}

}
