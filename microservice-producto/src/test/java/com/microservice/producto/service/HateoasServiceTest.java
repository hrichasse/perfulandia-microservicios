package com.microservice.producto.service;

import com.microservice.producto.dto.ProductoHateoasDTO;
import com.microservice.producto.model.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HateoasServiceTest {

    private final HateoasService hateoasService = new HateoasService();

    @Test
    void addLinksToProducto_deberiaRetornarDTOConLinks() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Producto Test");
        producto.setModelo("Modelo X");

        ProductoHateoasDTO dto = hateoasService.addLinksToProducto(producto);

        assertNotNull(dto);
        assertEquals(producto.getId(), dto.getId());
        assertEquals(producto.getName(), dto.getName());
        assertEquals(producto.getModelo(), dto.getModelo());
        assertFalse(dto.getLinks().isEmpty(), "El DTO debe tener enlaces HATEOAS");
        // Verificamos que existan los enlaces esperados
        List<String> rels = dto.getLinks().stream().map(Link::getRel).map(Object::toString).toList();
        assertTrue(rels.contains("self"));
        assertTrue(rels.contains("productos"));
        assertTrue(rels.contains("ventas"));
        assertTrue(rels.contains("update"));
        assertTrue(rels.contains("delete"));
    }

    @Test
    void addLinksToProductos_deberiaRetornarListaDeDTOs() {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setName("Producto 1");
        producto1.setModelo("Modelo 1");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setName("Producto 2");
        producto2.setModelo("Modelo 2");

        List<Producto> productos = Arrays.asList(producto1, producto2);
        List<ProductoHateoasDTO> dtos = hateoasService.addLinksToProductos(productos);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        for (int i = 0; i < productos.size(); i++) {
            assertEquals(productos.get(i).getId(), dtos.get(i).getId());
            assertFalse(dtos.get(i).getLinks().isEmpty());
        }
    }

    @Test
    void addLinksToProductos_listaVacia_deberiaRetornarListaVacia() {
        List<Producto> productos = new ArrayList<>();
        List<ProductoHateoasDTO> dtos = hateoasService.addLinksToProductos(productos);
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }
} 