package com.microservice.venta.repository;

import com.microservice.venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Método para obtener todas las ventas relacionadas con un productoId
    List<Venta> findAllByProductoId(Long productoId);  // Asegúrate de que devuelva List<Venta>
}
