package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmpleadosRepository extends CrudRepository<Empleado, Long> {
    Empleado findByDni(String dni);

    Page<Empleado> findAll(Pageable pageable);

    @Query("SELECT u FROM Empleado u WHERE (LOWER(u.nombre) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "OR LOWER(u.apellidos) LIKE LOWER(CONCAT('%', ?1, '%')))")
    Page<Empleado> searchByNameOrSurname(Pageable pageable, String searchText);

    @Query("SELECT u FROM Empleado u WHERE u.dni = ?1")
    Optional<Empleado> getByDni(String dni);
}
