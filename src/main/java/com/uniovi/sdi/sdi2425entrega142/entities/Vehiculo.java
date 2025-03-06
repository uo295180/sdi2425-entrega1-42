package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Vehiculos")
public class Vehiculo {

    @Id
    @GeneratedValue
    private Long id;


    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL)
    private Set<Trayecto> trayectos = new HashSet<>();

    public double getOdometro() {
        return 0;
    }

    public boolean tieneTrayectoEnCurso() {
        return trayectos.stream().filter( t -> t.isEstadoTrayecto() ).findFirst().isPresent();
    }
}
