package com.microservice.producto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title       = "API Microservice-Producto",
    version     = "v1",
    description = "CRUD de Productos",
    contact     = @Contact(name="Tu Nombre", email="tucorreo@ejemplo.com")
  )
)
public class OpenApiConfig {
    
}
