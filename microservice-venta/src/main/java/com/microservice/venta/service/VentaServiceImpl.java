package com.microservice.venta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.venta.model.Venta;
import com.microservice.venta.repository.VentaRepository;


@Service
public class VentaServiceImpl implements IVentaService{

    @Autowired
    private VentaRepository ventaRepository;

    @Override
    public List<Venta> findAll() {
        return (List<Venta>) ventaRepository.findAll();
    }

    @Override
    public Venta findById(Long id) {
        return ventaRepository.findById(id).orElseThrow();//Si no lo encuentra
        //Entonces lanza un error
    }

    @Override
    public void save(Venta venta) {
        ventaRepository.save(venta);
    }

    @Override
    public List<Venta> findByIdProducto(Long idProducto) {
        // asumiendo que idProducto es en realidad ventaId
        return ventaRepository.findAllByVentaId(idProducto);
    }

    public VentaRepository getVentaRepository() {
        return ventaRepository;
    }

    public void setVentaRepository(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> findByIdVenta(Long idVenta) {
        throw new UnsupportedOperationException("Unimplemented method 'findByIdVenta'");
    }

}
