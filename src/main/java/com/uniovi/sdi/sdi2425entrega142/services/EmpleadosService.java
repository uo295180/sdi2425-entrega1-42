package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.EmpleadosRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadosService {

    private final int PASSWORD_LENGTH = 12;

    private final EmpleadosRepository empleadosRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordGeneratorService passwordGeneratorService;
    private final RolesService rolesService;

    public EmpleadosService(EmpleadosRepository empleadosRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordGeneratorService passwordGeneratorService, RolesService rolesService) {
        this.empleadosRepository = empleadosRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordGeneratorService = passwordGeneratorService;
        this.rolesService = rolesService;
    }

    @PostConstruct
    public void init() {
    }
    public Page<Empleado> getEmpleados(Pageable pageable) {
        Page<Empleado> empleados = empleadosRepository.findAll(pageable);
        return empleados;
    }

    public Page<Empleado> getEmpleadosPaginados(int page) {
        int pageSize = 5; // 5 elementos por página
        Pageable pageable = PageRequest.of(page, pageSize);
        return empleadosRepository.findAll(pageable);
    }

    public Empleado getEmpleado(Long id) { return empleadosRepository.findById(id).get();}
    public Empleado getEmpleadoByDni(String dni) { return empleadosRepository.findByDni(dni).get();}
    public void addEmpleado(Empleado empleado) {
        if (empleado.getId() == null) {
            empleado.setPassword(bCryptPasswordEncoder.encode(empleado.getPassword()));
        }
        empleadosRepository.save(empleado);
    }
    public void deleteEmpleado(Long id) {
        empleadosRepository.deleteById(id);
    }

    public boolean existsEmpleado(String dni) {
        return empleadosRepository.getByDni(dni).isPresent();
    }

    public Page<Empleado> searchEmpleados(Pageable pageable, String searchText) {
        if(searchText != null && !searchText.trim().isEmpty()) {
            return empleadosRepository.searchByNameOrSurname(pageable, searchText);
        }
        return getEmpleados(pageable);
    }

    public String generatePassword() {
        return passwordGeneratorService.generateStrongPassword(PASSWORD_LENGTH);
    }

    public Optional<Empleado> findEmpleadoByDni(String dni) {
        return empleadosRepository.findEmpleadoByDni(dni);
    }

}
