package com.microservice.producto.controller;

import com.microservice.producto.model.Producto;
import com.microservice.producto.service.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)  // Esto asegura qu solo se cargue el controlador
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Mock se inyecta automaticamente por WebMvcTest

    @MockBean
    private IProductoService productoService;  //Aqui usamos MockBean para simular el servicio

    @Test
    void getAll_ReturnsListJson() throws Exception {
        // Crear lista simulada de productos
        List<Producto> productos = Arrays.asList(
            new Producto(1L, "Armani", "Code"),
            new Producto(2L, "Paco Rabanne", "Invictus")
        );

        // Simulamos la respuesta del servicio
        when(productoService.findAll()).thenReturn(productos);

        mockMvc.perform(get("/api/v1/producto/all"))  // Asegúrate de que la ruta sea correcta
               .andExpect(status().isOk())  // Verificar estado 200 OK
               .andExpect(jsonPath("$.length()").value(2))  // Verificar que hay dos productos
               .andExpect(jsonPath("$[0].name").value("Armani"))  // Verificar el nombre del primer producto
               .andExpect(jsonPath("$[1].name").value("Paco Rabanne"));  // Verificar el nombre del segundo producto
    }

    @Test
    void getById_ReturnsProductoJson() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Armani");
        producto.setModelo("Code");

        // Simulamos la respuesta del servicio
        when(productoService.findById(1L)).thenReturn(producto);

        mockMvc.perform(get("/api/v1/producto/search/{id}", 1L))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Armani"))
               .andExpect(jsonPath("$.modelo").value("Code"));
    }

    @Test
void getById_NotFound() throws Exception {
    // Simulamos que no se encuentra el producto
    when(productoService.findById(42L)).thenThrow(new EntityNotFoundException("Producto no encontrado"));

    // Realizamos la solicitud GET y verificamos el resultado
    mockMvc.perform(get("/api/v1/producto/search/{id}", 42L))
           .andExpect(status().isNotFound())  // Verifica que la respuesta sea 404
           .andExpect(content().string("Producto no encontrado"));  // Verifica que el mensaje de error sea el esperado
}

}
