package com.microservice.producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservice.producto.client.VentaClient;
import com.microservice.producto.dto.VentaDTO;
import com.microservice.producto.http.response.VentaByProductoResponse;
import com.microservice.producto.model.Producto;
import com.microservice.producto.repository.IProductoRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoRepository iProductoRepository;

    @Autowired
    private VentaClient ventaClient;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) iProductoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        // Lanzar excepción si no se encuentra el producto
        return iProductoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + id + " no encontrado"));
    }

    @Override
    public void save(Producto producto) {
        iProductoRepository.save(producto);
    }

    @Override
    public VentaByProductoResponse findVentasByIdProducto(Long idProducto) {
        // Buscar el producto; si no se encuentra, lanzamos una excepción
        Producto producto = iProductoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto con ID " + idProducto + " no encontrado"));

        // Consultar ventas por producto usando el cliente
        List<VentaDTO> ventaDTOList = ventaClient.findAllVentaByProducto(idProducto);

        // Construir y devolver la respuesta
        return VentaByProductoResponse.builder()
                .productoName(producto.getName())
                .modelo(producto.getModelo())
                .ventaDTOList(ventaDTOList)
                .build();
    }
}
