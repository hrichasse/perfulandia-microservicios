package com.microservice.venta.service;

import java.util.List;

import com.microservice.venta.model.Venta;

public interface IVentaService {

    List<Venta> findAll();

    Venta findById(Long id);
    
    void save(Venta ventas);

    List<Venta> findByIdCourse(Long idProducto);

    Object findByIdProducto(Long productoId);

}
