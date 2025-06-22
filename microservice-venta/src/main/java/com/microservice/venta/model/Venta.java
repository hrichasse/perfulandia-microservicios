package com.microservice.venta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@Table(name="ventas")
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

 @Id 
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String idventa;

  @Column(name = "venta_id")
  private Long ventaId;

  @Column(name = "producto_id")
  private Long productoId;
}