package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrayectosRepository extends CrudRepository<Trayecto, Long> {

    @Query("SELECT t FROM Trayecto t WHERE t.empleado=?1 AND t.estadoTrayecto=true")
    Optional<Trayecto> findTrayectoActivoByUser(Empleado empleado);

    @Query("SELECT t FROM Trayecto t WHERE t.empleado.id = ?1 ORDER BY t.id ASC")
    Page<Trayecto> findAllByEmpleadoId(Pageable pageable, Long id);

    Page<Trayecto> findAll(Pageable pageable);

    @Modifying
    @Query("DELETE FROM Trayecto t WHERE t.vehiculo.id = ?1")
    void deleteByVehiculoId(Long id);
}
