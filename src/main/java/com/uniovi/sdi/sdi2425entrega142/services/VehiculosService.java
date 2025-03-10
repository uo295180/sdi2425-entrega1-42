package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.VehiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VehiculosService {
    @Autowired
    VehiculosRepository vehiculosRepository;

    public Page<Vehiculo> getVehiculos(Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculosRepository.findAll(pageable);
        return vehiculos;
    }
    public Vehiculo getVehiculo(Long id) {
        return vehiculosRepository.findById(id).get();
    }
    public void addVehiculo(Vehiculo vehiculo) {
        // Si en Id es null le asignamos el último + 1 de la lista
        vehiculosRepository.save(vehiculo);
    }
    public void deleteVehiculo(Long id) {
        vehiculosRepository.deleteById(id);
    }
    public Vehiculo getVehiculoByMatricula(String matricula) {
        return vehiculosRepository.findByMatricula(matricula);
    }
    public Vehiculo getVehiculoByNumeroBastidor(String numeroBastidor) {
        return vehiculosRepository.findByNumeroBastidor(numeroBastidor);
    }
}
