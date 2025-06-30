package com.microservice.venta.repository;

import com.microservice.venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Método para buscar todas las ventas asociadas a un producto
    List<Venta> findAllByProductoId(Long productoId);
}
