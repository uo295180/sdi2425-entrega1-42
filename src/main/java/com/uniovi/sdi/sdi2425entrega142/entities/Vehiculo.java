package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Vehiculo")
public class Vehiculo {

    public enum TipoCombustible {
        GASOLINA, DIESEL, MICROHIBRIDO, HIBRIDO, ELECTRICO, GLP, GNR
    }

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique=true)
    private String matricula;
    private String numeroBastidor;
    private String marca;
    private String modelo;
    @Enumerated(EnumType.STRING)
    private TipoCombustible tipoCombustible;
    private double cantidadMaximaTanque;
    private double odometro;
    private double consumoMedio;
    private double cantidadTanque;
    private boolean estadoVehiculo;


    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.MERGE)
    private Set<Trayecto> trayectos = new HashSet<>();
    @OneToMany(mappedBy = "vehiculo", cascade  = CascadeType.MERGE)
    private Set<Repostaje> repostajes = new HashSet<>();

    public Vehiculo() {estadoVehiculo = false;}

    public Vehiculo(double cantidadMaximaTanque, double consumoMedio, String marca, String matricula, String modelo, String numeroBastidor, TipoCombustible tipoCombustible) {
        this.cantidadMaximaTanque = cantidadMaximaTanque;
        this.consumoMedio = consumoMedio;
        this.marca = marca;
        this.matricula = matricula;
        this.modelo = modelo;
        this.numeroBastidor = numeroBastidor;
        this.tipoCombustible = tipoCombustible;
        this.estadoVehiculo = false;
        this.cantidadTanque = cantidadMaximaTanque;
        this.odometro = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getCantidadMaximaTanque() {
        return cantidadMaximaTanque;
    }

    public void setCantidadMaximaTanque(double cantidadMaximaTanque) {
        this.cantidadMaximaTanque = cantidadMaximaTanque;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public double getCantidadTanque() {
        return cantidadTanque;
    }

    public void setCantidadTanque(double cantidadTanque) {
        this.cantidadTanque = cantidadTanque;
    }

    public boolean isEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(boolean estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroBastidor() {
        return numeroBastidor;
    }

    public void setNumeroBastidor(String numeroBastidor) {
        this.numeroBastidor = numeroBastidor;
    }

    public double getOdometro() {
        return odometro;
    }

    public void setOdometro(double odometro) {
        this.odometro = odometro;
    }

    public Set<Repostaje> getRepostajes() {
        return new HashSet<>(repostajes);
    }

    public void setRepostajes(Set<Repostaje> repostajes) {
        this.repostajes = repostajes;
    }

    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    public void setTipoCombustible(TipoCombustible tipoCombustible) {
        this.tipoCombustible = tipoCombustible;
    }

    public Set<Trayecto> getTrayectos() {
        return new HashSet<>(trayectos);
    }

    public void setTrayectos(Set<Trayecto> trayectos) {
        this.trayectos = trayectos;
    }

    public double getCantidadTanqueTrasGasto(double odometroTrasGasto) {
        double cantidadGastada = 0;
        if(repostajes.isEmpty()) {
            cantidadGastada = (odometroTrasGasto * consumoMedio) / 100;
        } else {
            Repostaje ultimoRepostaje = repostajes.stream().max( (r1, r2 )-> Math.toIntExact(r2.getFechaHoraRepostaje().getTime() - r1.getFechaHoraRepostaje().getTime())).get();
            cantidadGastada = ((odometroTrasGasto- ultimoRepostaje.getOdometro()) * consumoMedio) / 100;
        }
        return cantidadGastada > cantidadTanque ? 0 : cantidadTanque - cantidadGastada;
    }

    public boolean tanqueLleno() {
        return cantidadTanque == cantidadMaximaTanque;
    }

    public void respostar(Repostaje repostaje) {
        if(estadoVehiculo) { throw new IllegalStateException("No se puede repostar un vehículo que no está en uso"); }
        repostajes.add(repostaje);
        setCantidadTanque(this.cantidadTanque + repostaje.getCantidadRepostada());
        setOdometro(repostaje.getOdometro());
    }

    public String getEstadoFormulario() {
        if (!estadoVehiculo) {
            return "LIBRE";
        }
        return "OCUPADO";
    }

    public void addTrayecto(Trayecto trayecto) {
        trayectos.add(trayecto);
    }
}
