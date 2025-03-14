package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.repository.TrayectosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.LinkedList;

@Service
public class TrayectosService {

    private final TrayectosRepository trayectosRepository;

    public TrayectosService(TrayectosRepository trayectosRepository) {
        this.trayectosRepository = trayectosRepository;
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
}
