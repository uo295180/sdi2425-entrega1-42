package com.uniovi.sdi.sdi2425entrega142.repository;

import com.uniovi.sdi.sdi2425entrega142.entities.Repostaje;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepostajesRepository extends CrudRepository<Repostaje, Long> {
    Page<Repostaje> findAll(Pageable pageable);

    @Query("SELECT r FROM Repostaje r WHERE LOWER(r.vehiculo.matricula) LIKE LOWER(?1) ORDER BY r.fechaHoraRepostaje DESC")
    Page<Repostaje> findByVehiculo(Pageable pageable, String matricula);

    @Query("SELECT r FROM Repostaje r WHERE LOWER(r.vehiculo.matricula) LIKE LOWER(?1) ORDER BY r.fechaHoraRepostaje DESC")
    Optional<Repostaje> findLastRepostajeByVehiculo(String matricula);
}
