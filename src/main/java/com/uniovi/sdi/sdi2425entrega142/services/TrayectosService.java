package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.TrayectosRepository;
import com.uniovi.sdi.sdi2425entrega142.repository.VehiculosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrayectosService {

    private final TrayectosRepository trayectosRepository;
    private final VehiculosRepository vehiculosRepository;

    public TrayectosService(TrayectosRepository trayectosRepository, VehiculosRepository vehiculosRepository) {
        this.trayectosRepository = trayectosRepository;
        this.vehiculosRepository = vehiculosRepository;
    }

    public Page<Trayecto> getTrayectos(Pageable pageable) {
        return trayectosRepository.findAll(pageable);
    }

    public Trayecto getTrayecto(Long id) {
        return trayectosRepository.findById(id).isPresent() ? trayectosRepository.findById(id).get() : new Trayecto();
    }

    public void addTrayecto(Trayecto trayecto) {
        trayectosRepository.save(trayecto);
    }

    public Page<Trayecto> getTrayectosDelEmpleado(Pageable pageable, Empleado empleado) {
        Page<Trayecto> trayectos = new PageImpl<>(new LinkedList<>());
        if (empleado.getRole().equals("ROLE_ESTANDAR")) {
            trayectos = trayectosRepository.findAllByEmpleadoId(pageable, empleado.getId());
        }
        if (empleado.getRole().equals("ROLE_ADMIN")) {
            trayectos = getTrayectos(pageable);
        }
        return trayectos;
    }

    public Optional<Trayecto> findTrayectoActivoByUser(Empleado empleado) {
        return trayectosRepository.findTrayectoActivoByUser(empleado);
    }

    public Page<Trayecto> getTrayectosByVehiculo(Long vehiculoId, Pageable pageable) {
        return trayectosRepository.findByVehiculoId(vehiculoId, pageable);
    }

    public List<Trayecto> findTrayectosFinalizadosPorVehiculo(Long id) {
        Vehiculo v = vehiculosRepository.findById(id).orElse(null);
        if (v == null) return Collections.emptyList();
        List<Trayecto> trayectosFinalizados = new ArrayList<>();
        for (Trayecto trayecto : v.getTrayectos()) {
            if (!trayecto.isEstadoTrayecto()) {
                trayectosFinalizados.add(trayecto);
            }
        }
        return trayectosFinalizados;
    }
}
