package com.microservice.venta.service;

import java.util.List;

import com.microservice.venta.model.Venta;

public interface IVentaService {

    List<Venta> findAll();

    Venta findById(Long id);
    
    void save(Venta venta);

    List<Venta> findByIdVenta(Long idProducto);

    Object findByIdProducto(Long productoId);

}
