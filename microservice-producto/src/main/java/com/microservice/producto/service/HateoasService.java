package com.microservice.producto.service;

import com.microservice.producto.dto.ProductoHateoasDTO;
import com.microservice.producto.model.Producto;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class HateoasService {

    public ProductoHateoasDTO addLinksToProducto(Producto producto) {
        ProductoHateoasDTO dto = new ProductoHateoasDTO();
        dto.setId(producto.getId());
        dto.setName(producto.getName());
        dto.setModelo(producto.getModelo());
        
        // Agregar enlaces HATEOAS
        dto.add(linkTo(methodOn(com.microservice.producto.controller.ProductoController.class)
                .findById(producto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(com.microservice.producto.controller.ProductoController.class)
                .findAllProducto()).withRel("productos"));
        dto.add(linkTo(methodOn(com.microservice.producto.controller.ProductoController.class)
                .findVentasByIdProducto(producto.getId())).withRel("ventas"));
        dto.add(linkTo(methodOn(com.microservice.producto.controller.ProductoController.class)
                .updateProducto(producto.getId(), null)).withRel("update"));
        dto.add(linkTo(methodOn(com.microservice.producto.controller.ProductoController.class)
                .deleteProducto(producto.getId())).withRel("delete"));
        
        return dto;
    }
    
    public List<ProductoHateoasDTO> addLinksToProductos(List<Producto> productos) {
        return productos.stream()
                .map(this::addLinksToProducto)
                .collect(Collectors.toList());
    }
} 