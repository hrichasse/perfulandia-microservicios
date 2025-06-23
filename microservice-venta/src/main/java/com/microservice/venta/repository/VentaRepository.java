package com.microservice.venta.repository;

import com.microservice.venta.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    // ¡Quita la @Query y deja que Spring derive la consulta!
    List<Venta> findAllByVentaId(Long ventaId);

    }