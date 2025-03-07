package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.repository.EmpleadosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadosService {

    private final EmpleadosRepository empleadosRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EmpleadosService(EmpleadosRepository empleadosRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.empleadosRepository = empleadosRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
    }

    public Page<Empleado> getEmpleados(Pageable pageable) {
        return empleadosRepository.findAll(pageable);
    }

    public Empleado getEmpleado(Long id) { return empleadosRepository.findById(id).get();}

    public Empleado getEmpleadoByDni(String dni) {
        return empleadosRepository.findByDni(dni);
    }


    public void addEmpleado(Empleado empleado) {
        empleado.setPassword(bCryptPasswordEncoder.encode(empleado.getPassword()));
        empleadosRepository.save(empleado);
    }

    public void deleteEmpleado(Long id) {
        empleadosRepository.deleteById(id);
    }
}
