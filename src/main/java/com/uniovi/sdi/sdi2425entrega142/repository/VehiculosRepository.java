package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VehiculosRepository extends CrudRepository<Vehiculo, Long> {


    @Query("SELECT v FROM Vehiculo v WHERE v.estadoVehiculo = false ORDER BY v.id ASC")
    Page<Vehiculo> findByAvailability(Pageable pageable);
    Vehiculo findByMatricula(String matricula);
    Vehiculo findByNumeroBastidor(String numeroBastidor);
    Page<Vehiculo> findAll(Pageable pageable);
}
