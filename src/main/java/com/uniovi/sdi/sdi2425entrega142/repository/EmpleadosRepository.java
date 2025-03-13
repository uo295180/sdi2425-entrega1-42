package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadosRepository extends CrudRepository<Empleado, Long> {
    Empleado findByDni(String dni);

    @Query("SELECT u FROM Empleado u WHERE (LOWER(u.nombre) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', ?1, '%')))")
    List<Empleado> searchByNameOrSurname(String searchText);

    @Query("SELECT u FROM Empleado u WHERE (LOWER(u.dni) LIKE LOWER(?1) )")
    Optional<Empleado> findEmpleadoByDni(String dni);
}
