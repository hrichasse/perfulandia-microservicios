package com.microservice.venta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.microservice.venta.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Long>{

    
    @Query("SELECT s FROM  s WHERE s.ventaId = :idVenta")
    List<Venta> findAllStudent(Long idVenta);

    List<Venta> findAllVenta(Long idProducto);

    //Query Method
    //List<Student> findAllByCourseId(Long courseId);

}
