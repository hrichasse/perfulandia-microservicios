package com.microservice.producto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservice.producto.dto.VentaDTO;

import java.util.List;

@FeignClient(name = "msvc-venta", url = "localhost:8090")
public interface VentaClient {

    
    @GetMapping("/api/v1/venta/search-by-producto/{productoId}")    
    List<VentaDTO> findAllVentaByProducto(@PathVariable Long productoId);

}
