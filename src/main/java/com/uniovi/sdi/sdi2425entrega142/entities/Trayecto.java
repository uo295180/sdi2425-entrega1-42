package com.uniovi.sdi.sdi2425entrega142.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Trayecto")
public class Trayecto {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaInicioTrayecto;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechaFinTrayecto;

    private long duracionTrayecto;
    private double distanciaTrayecto;
    private boolean estadoTrayecto;
    private double odometroInicio;
    private double odometroFin;
    private String observaciones;

    @OneToMany(mappedBy = "trayecto", cascade = CascadeType.MERGE)
    private Set<Incidencia> incidencias = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private Empleado empleado;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "vehiculo_matricula")
    private Vehiculo vehiculo;

    public Trayecto() {
    }

    public Trayecto(Empleado empleado, Vehiculo vehiculo) {
        this.empleado = empleado;
        this.vehiculo = vehiculo;
        this.fechaInicioTrayecto = LocalDateTime.now();
        this.odometroInicio = vehiculo.getOdometro();
        this.estadoTrayecto = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicioTrayecto() {
        return fechaInicioTrayecto;
    }

    public void setFechaInicioTrayecto(LocalDateTime fechaInicioTrayecto) {
        this.fechaInicioTrayecto = fechaInicioTrayecto;
    }

    public LocalDateTime getFechaFinTrayecto() {
        return fechaFinTrayecto;
    }

    public void setFechaFinTrayecto(LocalDateTime fechaFinTrayecto) {
        this.fechaFinTrayecto = fechaFinTrayecto;
    }

    public double getDistanciaTrayecto() {
        return distanciaTrayecto;
    }

    public void setDistanciaTrayecto(double distanciaTrayecto) {
        this.distanciaTrayecto = distanciaTrayecto;
    }

    public long getDuracionTrayecto() {
        return duracionTrayecto;
    }

    public void setDuracionTrayecto(long duracionTrayecto) {
        this.duracionTrayecto = duracionTrayecto;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public boolean isEstadoTrayecto() {
        return estadoTrayecto;
    }

    public void setEstadoTrayecto(boolean estadoTrayecto) {
        this.estadoTrayecto = estadoTrayecto;
    }

    public double getOdometroFin() {
        return odometroFin;
    }

    public void setOdometroFin(double odometroFin) {
        setDistanciaTrayecto(odometroFin - odometroInicio);
        this.odometroFin = odometroFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public double getOdometroInicio() {
        return odometroInicio;
    }

    public void setOdometroInicio(double odometroInicio) {
        this.odometroInicio = odometroInicio;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void endTrayecto(double odometroFin, String observaciones) {
        if (estadoTrayecto) {
            setEstadoTrayecto(false);
            setObservaciones(observaciones);
            setOdometroFin(odometroFin);
            setFechaFinTrayecto(LocalDateTime.now());

            // Calcular la duración del trayecto en segundos
            setDuracionTrayecto(Duration.between(fechaInicioTrayecto, fechaFinTrayecto).toSeconds());

            vehiculo.setEstadoVehiculo(false);
            vehiculo.setOdometro(odometroFin);
        } else {
            throw new IllegalStateException("Esta función no debe ser ejecutada en un trayecto que no esté en curso");
        }
    }

    public void crearIncidencia(String titulo, String descripcion, boolean estado) {
        if (!estadoTrayecto) {
            throw new IllegalStateException("No se puede crear una incidencia en un trayecto terminado");
        }
        incidencias.add(new Incidencia(titulo, descripcion, estado));
    }
}
