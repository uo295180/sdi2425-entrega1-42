package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.dtos.PasswordDTO;
import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.repository.EmpleadosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class EmpleadosService {

    private final int PASSWORD_LENGTH = 12;

    private final EmpleadosRepository empleadosRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PasswordGeneratorService passwordGeneratorService;

    public EmpleadosService(EmpleadosRepository empleadosRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordGeneratorService passwordGeneratorService) {
        this.empleadosRepository = empleadosRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passwordGeneratorService = passwordGeneratorService;
    }

    @PostConstruct
    public void init() {
    }
    public Page<Empleado> getEmpleados(Pageable pageable) {
        return empleadosRepository.findAll(pageable);
    }

    public Page<Empleado> getEmpleadosPaginados(int page) {
        int pageSize = 5; // 5 elementos por página
        Pageable pageable = PageRequest.of(page, pageSize);
        return empleadosRepository.findAll(pageable);
    }

    public Empleado getEmpleado(Long id) { return empleadosRepository.findById(id).get();}

    public void addEmpleado(Empleado empleado) {
        if (empleado.getId() == null) {
            empleado.setPassword(bCryptPasswordEncoder.encode(empleado.getPassword()));
        }
        empleadosRepository.save(empleado);
    }

    public boolean matchesPassword(String rawPassword, Long id) {
        Empleado empleado = getEmpleado(id);
        return bCryptPasswordEncoder.matches(rawPassword, empleado.getPassword());
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

    public Empleado getByDni(String dni) {
        return empleadosRepository.findByDni(dni);
    }

    public void changePassword(PasswordDTO dto) {
        Empleado empleado = getEmpleado(dto.getId());

        empleado.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));

        empleadosRepository.save(empleado);
    }

    public Optional<Empleado> findEmpleadoByDni(String dni) {
        return empleadosRepository.findEmpleadoByDni(dni);
    }

}
