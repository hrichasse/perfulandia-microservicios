package com.microservice.producto.dto;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VentaDTO {


    private String name;
    private String lastName;
    private String email;
    private Long ventaId;
}
