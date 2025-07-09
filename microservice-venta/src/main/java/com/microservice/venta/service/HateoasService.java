package com.microservice.venta.service;

import com.microservice.venta.dto.VentaHateoasDTO;
import com.microservice.venta.model.Venta;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasService {

    public VentaHateoasDTO addLinksToVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La venta no puede ser nula");
        }
        VentaHateoasDTO dto = new VentaHateoasDTO();
        dto.setId(venta.getId());
        dto.setName(venta.getName());
        dto.setIdventa(venta.getIdventa());
        dto.setVentaId(venta.getVentaId());
        dto.setProductoId(venta.getProductoId());
        // Agregar enlaces HATEOAS
        dto.add(linkTo(methodOn(com.microservice.venta.controller.VentaController.class)
                .findById(venta.getId())).withSelfRel());
        dto.add(linkTo(methodOn(com.microservice.venta.controller.VentaController.class)
                .findAllVentas()).withRel("ventas"));
        dto.add(linkTo(methodOn(com.microservice.venta.controller.VentaController.class)
                .findByProductoId(venta.getProductoId())).withRel("ventas-por-producto"));
        dto.add(linkTo(methodOn(com.microservice.venta.controller.VentaController.class)
                .saveVenta(null)).withRel("create"));
        return dto;
    }
    
    public List<VentaHateoasDTO> addLinksToVentas(List<Venta> ventas) {
        if (ventas == null || ventas.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return ventas.stream()
                .map(this::addLinksToVenta)
                .collect(Collectors.toList());
    }
} 