package com.microservice.producto.service;

import java.util.List;

import com.microservice.producto.http.response.VentaByProductoResponse;
import com.microservice.producto.model.Producto;

public interface IProductoService {

    List<Producto> findAll();

    Producto findById(Long id);

    Producto save(Producto producto);

    void deleteById(Long id);

    VentaByProductoResponse findVentasByIdProducto(Long idProducto);
}
