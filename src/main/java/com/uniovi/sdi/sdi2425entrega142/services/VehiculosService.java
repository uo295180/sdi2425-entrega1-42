package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.TrayectosRepository;
import com.uniovi.sdi.sdi2425entrega142.repository.VehiculosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculosService {

    private final VehiculosRepository vehiculosRepository;
    private final TrayectosRepository trayectosRepository;

    public VehiculosService(VehiculosRepository vehiculosRepository, TrayectosRepository trayectosRepository) {
        this.vehiculosRepository = vehiculosRepository;
        this.trayectosRepository = trayectosRepository;
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

    @Transactional
    public void deleteVehiculo(Long id) {
        trayectosRepository.deleteByVehiculoId(id);
        vehiculosRepository.deleteById(id);
    }

    public Vehiculo getVehiculoByMatricula(String matricula) {
        return vehiculosRepository.findByMatricula(matricula);
    }

    public Vehiculo getVehiculoByNumeroBastidor(String numeroBastidor) {
        return vehiculosRepository.findByNumeroBastidor(numeroBastidor);
    }

    public List<Vehiculo> findAll() {
        return vehiculosRepository.findAllVehiculos();
    }

    public List<Vehiculo> findVehiculosConTrayectosFinalizados() {
        List<Vehiculo> vehiculosList = (List<Vehiculo>) vehiculosRepository.findAll();
        List<Vehiculo> vehiculosConTrayectosFinalizados = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculosList) {
            for (Trayecto trayecto : vehiculo.getTrayectos()) {
                if (!trayecto.isEstadoTrayecto() && !vehiculosConTrayectosFinalizados.contains(vehiculo)) {
                    vehiculosConTrayectosFinalizados.add(vehiculo);
                }
            }
        }
        return vehiculosConTrayectosFinalizados;
    }
}
