package com.microservice.venta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class VentaHateoasDTO extends RepresentationModel<VentaHateoasDTO> {
    
    private Long id;
    private String name;
    private String idventa;
    private Long ventaId;
    private Long productoId;
} 