package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.VehiculosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculosService {

    private final VehiculosRepository vehiculosRepository;

    public VehiculosService(VehiculosRepository vehiculosRepository) {
        this.vehiculosRepository = vehiculosRepository;
    }

    public Page<Vehiculo> getVehiculos(Pageable pageable) {
        return vehiculosRepository.findAll(pageable);
    }

    public Vehiculo getVehiculo(Long id) {
        return vehiculosRepository.findById(id).isPresent() ? vehiculosRepository.findById(id).get() : new Vehiculo();
    }

    public void addVehiculo(Vehiculo vehiculo) {
        vehiculosRepository.save(vehiculo);
    }

    public Page<Vehiculo> getVehiculosDisponibles(Pageable pageable) {
        return vehiculosRepository.findByAvailability(pageable);
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
