package com.microservice.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.producto.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto , Long>{
    
}
