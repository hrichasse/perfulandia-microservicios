package com.microservice.producto.service;

import java.util.List;

import com.microservice.producto.http.response.VentaByProductoResponse;
import com.microservice.producto.model.Producto;

public interface IProductoService {

    List<Producto> findAll();

    Producto findById(Long id);

    void save(Producto producto);

    VentaByProductoResponse findVentasByIdProducto(Long idProducto);
}
