package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;

@Entity
@Table(name="Incidencias")
public class Incidencia {

    enum EstadoIncidencia {
        REGISTRADA,LEIDA,EN_PROCESO,RESUELTA
    }

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String descripcion;
    private boolean tipo;
    private EstadoIncidencia estado = EstadoIncidencia.REGISTRADA;
    private String respuesta;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="trayecto_id")
    private Trayecto trayecto;

    public Incidencia() {}

    public Incidencia(String titulo, String descripcion, boolean tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
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
}
