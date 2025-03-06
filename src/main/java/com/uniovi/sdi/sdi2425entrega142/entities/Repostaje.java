package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="Repostajes")
public class Repostaje {

    @Id
    @GeneratedValue
    private Long id;

    private Timestamp fechaHoraRepostaje;
    private double odometro;

    public Timestamp getFechaHoraRepostaje() {
        return fechaHoraRepostaje;
    }

    public void setFechaHoraRepostaje(Timestamp fechaHoraRepostaje) {
        this.fechaHoraRepostaje = fechaHoraRepostaje;
    }

    public double getOdometro() {
        return odometro;
    }

    public void setOdometro(double odometro) {
        this.odometro = odometro;
    }
}
