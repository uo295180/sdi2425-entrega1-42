package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.repository.TrayectosRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrayectosService {

    private final TrayectosRepository trayectosRepository;

    public TrayectosService(TrayectosRepository trayectosRepository) {
        this.trayectosRepository = trayectosRepository;
    }

    public void addTrayecto(Trayecto trayecto) {
        trayectosRepository.save(trayecto);
    }

    public Optional<Trayecto> findTrayectoActivoByUser(Empleado empleado) {
        return trayectosRepository.findTrayectoActivoByUser(empleado);
    }
}
