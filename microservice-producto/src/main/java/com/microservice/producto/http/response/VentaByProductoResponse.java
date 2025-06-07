package com.microservice.producto.http.response;

import java.util.List;

import com.microservice.producto.dto.VentaDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor

public class VentaByProductoResponse {
    private String productoName;
    private String modelo;
    private List<VentaDTO> ventaDTOList;
}
