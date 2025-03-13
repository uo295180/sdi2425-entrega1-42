package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Trayectos" )
public class Trayecto {

    @Id
    @GeneratedValue
    private Long id;
    private Timestamp fechaInicioTrayecto;
    private Timestamp fechaFinTrayecto;
    private long duracionTrayecto;
    private double distanciaTrayecto;
    private boolean estadoTrayecto;
    private double odometroInicio;
    private double odometroFin;
    private String observaciones;

    @OneToMany(mappedBy="trayecto", cascade=CascadeType.ALL)
    private Set<Incidencia> incidencias = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Empleado empleado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="matricula")
    private Vehiculo vehiculo;

    public Trayecto() {}

    public Trayecto(Empleado empleado, Vehiculo vehiculo) {
        this.empleado = empleado;
        this.vehiculo = vehiculo;
        this.fechaInicioTrayecto = new Timestamp(System.currentTimeMillis());
        this.odometroInicio = vehiculo.getOdometro();
        this.estadoTrayecto = true;
        vehiculo.setEstadoVehiculo(true);
        empleado.addTrayecto(this);
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

    public Timestamp getFechaInicioTrayecto() {
        return fechaInicioTrayecto;
    }

    public void setFechaInicioTrayecto(Timestamp fechaInicioTrayecto) {
        this.fechaInicioTrayecto = fechaInicioTrayecto;
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

    public Timestamp getFechaFinTrayecto() {
        return fechaFinTrayecto;
    }

    public void setFechaFinTrayecto(Timestamp fechaFinTrayecto) {
        this.fechaFinTrayecto = fechaFinTrayecto;
    }

    public void endTrayecto(double odometroFin, String observaciones) {
        if(estadoTrayecto) {
            setEstadoTrayecto(false);
            setObservaciones(observaciones);
            setOdometroFin(odometroFin);
            setFechaFinTrayecto(new Timestamp(System.currentTimeMillis()));
            setDuracionTrayecto(fechaFinTrayecto.getTime() - fechaInicioTrayecto.getTime());
            vehiculo.setEstadoVehiculo(false);
        } else {
            throw new IllegalStateException("Esta función no debe ser ejecutada en un trayecto que no esté en curso");
        }
    }

    public void crearIncidencia(String titulo, String descricion, boolean estado) {
        if(!estadoTrayecto) { throw new IllegalStateException("No se puede crear una incidencia en un trayecto terminado"); }
        incidencias.add(new Incidencia(titulo, descricion, estado));
    }
}
