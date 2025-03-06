package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadosRepository extends CrudRepository<Empleado, Long> {
    Empleado findByDni(String dni);
}
