package com.microservice.producto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.producto.client.VentaClient;
import com.microservice.producto.dto.VentaDTO;
import com.microservice.producto.http.response.VentaByProductoResponse;
import com.microservice.producto.model.Producto;
import com.microservice.producto.repository.IProductoRepository;

import java.util.List;

@Service
public class ProductoServiceImpl implements IProductoService{

    @Autowired
    private IProductoRepository iProductoRepository;

    @Autowired
    private VentaClient ventaClient;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) iProductoRepository.findAll();
    }

    @Override
    public Producto findById(Long id) {
        return iProductoRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Producto producto) {
        iProductoRepository.save(producto);
    }

    @Override
    public VentaByProductoResponse findVentasByIdProducto(Long idProducto){


        //Consultar el curso
        //Porque devuelve un optional
        Producto producto = iProductoRepository.findById(idProducto).orElse(new Producto());

        //Obtener los estudiantes que estan en el curso obtenido
        List<VentaDTO> ventaDTOList = ventaClient.findAllVentaByProducto(idProducto);


        return VentaByProductoResponse.builder()
                .productoName(producto.getName())
                .modelo(producto.getModelo())
                .ventaDTOList(ventaDTOList)
                .build();
    }
}
