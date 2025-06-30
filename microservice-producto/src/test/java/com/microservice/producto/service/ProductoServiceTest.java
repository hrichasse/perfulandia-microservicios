package com.microservice.producto.service;

import com.microservice.producto.model.Producto;
import com.microservice.producto.repository.IProductoRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private IProductoRepository repo;  // Repositorio simulado

    @InjectMocks
    private ProductoServiceImpl service;  // El servicio real, no la interfaz

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    void findByIdExisting() {
        // Preparar: producto "Armani Code"
        Producto p = new Producto();
        p.setId(1L);
        p.setName("Armani");
        p.setModelo("Code");
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        // Acción: Llamada al método que se va a probar
        Producto result = service.findById(1L);

        // Verificación: Asegurarnos de que los resultados son correctos
        assertNotNull(result);
        assertEquals("Armani", result.getName());
        assertEquals("Code", result.getModelo());
        verify(repo).findById(1L);  // Verificamos que el método fue llamado
    }

    @Test
    void findByIdNotFound() {
        // Preparar: El repositorio devuelve un Optional vacío
        when(repo.findById(anyLong())).thenReturn(Optional.empty());

        // Acción y Verificación: Esperamos que se lance una EntityNotFoundException
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
                                                         () -> service.findById(42L));
        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("Producto con ID 42 no encontrado", exception.getMessage());
        
        // Verificamos que findById haya sido llamado con el ID 42
        verify(repo).findById(42L);
    }

    @Test
    void saveProducto() {
        // Dado: Un producto a guardar
        Producto p = new Producto();
        p.setId(1L);
        p.setName("Armani");
        p.setModelo("Code");

        // Cuando: El método save del servicio es llamado
        service.save(p);

        // Entonces: Verificamos que el método save del repositorio sea llamado
        verify(repo, times(1)).save(p);
    }
}
