package com.microservice.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductoHateoasDTO extends RepresentationModel<ProductoHateoasDTO> {
    
    private Long id;
    private String name;
    private String modelo;
} 