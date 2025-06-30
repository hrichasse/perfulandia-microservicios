package com.microservice.venta.service;

import com.microservice.venta.model.Venta;
import com.microservice.venta.repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServiceImpl implements IVentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> findAll() {
        return (List<Venta>) ventaRepository.findAll();
    }

    @Override
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Venta con ID " + id + " no encontrada"));
    }

    @Override
    public void save(Venta venta) {
        ventaRepository.save(venta);
    }

    @Override
    public List<Venta> findByIdProducto(Long idProducto) {
        // Buscar las ventas asociadas al producto
        List<Venta> ventas = ventaRepository.findAllByProductoId(idProducto);

        // Si no se encuentran ventas, lanzamos una excepción
        if (ventas == null || ventas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron ventas para el producto con ID " + idProducto);
        }

        return ventas;
    }

    @Override
    public List<Venta> findByIdVenta(Long idVenta) {
        throw new UnsupportedOperationException("Método findByIdVenta no implementado");
    }

    


}
