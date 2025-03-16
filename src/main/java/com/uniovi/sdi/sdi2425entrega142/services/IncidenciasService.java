package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Incidencia;
import com.uniovi.sdi.sdi2425entrega142.repository.IncidenciasRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class IncidenciasService {
    private final IncidenciasRepository incidenciasRepository;

    public IncidenciasService(IncidenciasRepository incidenciasRepository) {
        this.incidenciasRepository = incidenciasRepository;
    }

    public List<Incidencia> getIncidencias() {
        List<Incidencia> incidencias = new ArrayList<>();
        incidenciasRepository.findAll().forEach(incidencias::add);

        // Definir el orden de los estados
        List<Incidencia.EstadoIncidencia> ordenEstados = List.of(
                Incidencia.EstadoIncidencia.REGISTRADA,
                Incidencia.EstadoIncidencia.LEIDA,
                Incidencia.EstadoIncidencia.EN_PROCESO,
                Incidencia.EstadoIncidencia.RESUELTA
        );

        // Ordenar la lista usando Comparator y el índice en ordenEstados
        incidencias.sort(Comparator.comparing(
                incidencia -> ordenEstados.indexOf(incidencia.getEstado())
        ));

        return incidencias;
    }


    public Incidencia getIncidencia(Long id) {
        return incidenciasRepository.findById(id).isPresent() ? incidenciasRepository.findById(id).get() : new Incidencia();
    }

    public void addIncidencia(Incidencia incidencia) {
        incidenciasRepository.save(incidencia);
    }
    public void deleteIncidencia(Long id) {
        incidenciasRepository.deleteById(id);
    }
}