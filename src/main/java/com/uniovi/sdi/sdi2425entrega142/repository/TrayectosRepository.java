package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TrayectosRepository extends CrudRepository<Trayecto, Long> {
    @Query("SELECT t FROM Trayecto t WHERE t.empleado = ?1 ORDER BY t.id ASC")
    Page<Trayecto> findAllByEmpleado(Pageable pageable, Empleado empleado);

    Page<Trayecto> findAll(Pageable pageable);
}
