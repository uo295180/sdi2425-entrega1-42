package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="Repostajes")
public class Repostaje {

    @Id
    @GeneratedValue
    private Long id;
    private String nombreEstacion;
    private double precio;
    private double cantidadRepostada;
    private boolean repostajeCompleto;
    private String observaciones;
    private Timestamp fechaHoraRepostaje;
    private double odometro;
    private double precioTotal;

    @ManyToOne
    private Vehiculo vehiculo;

    public Repostaje() {}

    public Repostaje(double cantidadRepostada, String nombreEstacion, String observaciones, double odometro, double precio, Vehiculo vehiculo) {
        this.cantidadRepostada = cantidadRepostada;
        this.nombreEstacion = nombreEstacion;
        this.observaciones = observaciones;
        this.odometro = odometro;
        this.precio = precio;
        this.vehiculo = vehiculo;
        this.fechaHoraRepostaje = new Timestamp(System.currentTimeMillis());
        this.precioTotal = precio * cantidadRepostada;
        vehiculo.respostar(this);
        this.repostajeCompleto = vehiculo.tanqueLleno();
    }

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

    public double getCantidadRepostada() {
        return cantidadRepostada;
    }

    public void setCantidadRepostada(double cantidadRepostada) {
        this.cantidadRepostada = cantidadRepostada;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public void setNombreEstacion(String nombreEstacion) {
        this.nombreEstacion = nombreEstacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isRepostajeCompleto() {
        return repostajeCompleto;
    }

    public void setRepostajeCompleto(boolean repostajeCompleto) {
        this.repostajeCompleto = repostajeCompleto;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
