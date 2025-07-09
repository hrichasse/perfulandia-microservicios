package com.microservice.producto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class HateoasConfig {
    
    // Esta configuración habilita el soporte HATEOAS con formato HAL
    // HAL (Hypertext Application Language) es el formato estándar para HATEOAS
} 