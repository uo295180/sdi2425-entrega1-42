package com.uniovi.sdi.sdi2425entrega142.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="Incidencia")
public class Incidencia {

    public enum EstadoIncidencia {
        REGISTRADA,LEIDA,EN_PROCESO,RESUELTA
    }

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String descripcion;
    private boolean tipo;
    private EstadoIncidencia estado;
    private String respuesta;
    private Timestamp fechaHoraIncidencia;

    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="trayecto_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Trayecto trayecto;



    public Incidencia() {this.estado = EstadoIncidencia.REGISTRADA;}

    public Incidencia(String titulo, String descripcion, boolean tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.estado = EstadoIncidencia.REGISTRADA;
        this.respuesta = "";
    }

    public Incidencia(Timestamp fechaHoraIncidencia, String titulo, String descripcion, boolean tipo, EstadoIncidencia estado, String respuesta, Trayecto trayecto) {
        this.fechaHoraIncidencia = fechaHoraIncidencia;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.estado = estado;
        this.respuesta = respuesta;
        this.trayecto = trayecto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Trayecto getTrayecto() {
        return trayecto;
    }

    public void setTrayecto(Trayecto trayecto) {
        this.trayecto = trayecto;
    }

    public Timestamp getFechaHoraIncidencia() {
        return fechaHoraIncidencia;
    }

    public void setFechaHoraIncidencia(Timestamp fechaHoraIncidencia) {
        this.fechaHoraIncidencia = fechaHoraIncidencia;
    }

    public String getTipoFormulario() {
        if (tipo) {
            return "ESPERADA";
        }
        return "NO ESPERADA";
    }
    public boolean getEstadoFormulario() {
        return estado == EstadoIncidencia.RESUELTA;
    }
    public void marcarLeida() {
        if (estado == EstadoIncidencia.REGISTRADA) {
            setEstado(EstadoIncidencia.LEIDA);
        }
    }
    public String getEstadoString() {
        return String.valueOf(estado);
    }

}