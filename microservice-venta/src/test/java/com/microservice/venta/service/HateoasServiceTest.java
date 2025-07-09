package com.microservice.venta.service;

import com.microservice.venta.dto.VentaHateoasDTO;
import com.microservice.venta.model.Venta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HateoasServiceTest {
    private HateoasService hateoasService;

    @BeforeEach
    void setUp() {
        hateoasService = new HateoasService();
    }

    @Test
    void addLinksToVenta_deberiaAgregarLinksCorrectos() {
        Venta venta = new Venta();
        venta.setId(1L);
        venta.setName("Venta Test");
        venta.setIdventa("IDV-1");
        venta.setVentaId(100L);
        venta.setProductoId(200L);

        VentaHateoasDTO dto = hateoasService.addLinksToVenta(venta);

        assertEquals(1L, dto.getId());
        assertEquals("Venta Test", dto.getName());
        assertEquals("IDV-1", dto.getIdventa());
        assertEquals(100L, dto.getVentaId());
        assertEquals(200L, dto.getProductoId());
        assertFalse(dto.getLinks().isEmpty());
        assertTrue(dto.getLinks().stream().anyMatch(l -> l.getRel().value().equals("self")));
        assertTrue(dto.getLinks().stream().anyMatch(l -> l.getRel().value().equals("ventas")));
        assertTrue(dto.getLinks().stream().anyMatch(l -> l.getRel().value().equals("ventas-por-producto")));
        assertTrue(dto.getLinks().stream().anyMatch(l -> l.getRel().value().equals("create")));
    }

    @Test
    void addLinksToVenta_conVentaNula_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> hateoasService.addLinksToVenta(null));
    }

    @Test
    void addLinksToVenta_conCamposNulos_noLanzaExcepcion() {
        Venta venta = new Venta();
        // Todos los campos nulos
        assertDoesNotThrow(() -> hateoasService.addLinksToVenta(venta));
    }

    @Test
    void addLinksToVentas_listaNormal() {
        Venta venta1 = new Venta();
        venta1.setId(1L);
        Venta venta2 = new Venta();
        venta2.setId(2L);
        List<Venta> ventas = List.of(venta1, venta2);
        List<VentaHateoasDTO> dtos = hateoasService.addLinksToVentas(ventas);
        assertEquals(2, dtos.size());
        assertEquals(1L, dtos.get(0).getId());
        assertEquals(2L, dtos.get(1).getId());
    }

    @Test
    void addLinksToVentas_listaVacia() {
        List<VentaHateoasDTO> dtos = hateoasService.addLinksToVentas(List.of());
        assertTrue(dtos.isEmpty());
    }

    @Test
    void addLinksToVentas_listaNula() {
        List<VentaHateoasDTO> dtos = hateoasService.addLinksToVentas(null);
        assertTrue(dtos.isEmpty());
    }
} 