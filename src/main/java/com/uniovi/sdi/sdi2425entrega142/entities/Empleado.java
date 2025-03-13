package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Empleados")
public class Empleado {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String dni;
    private String nombre;
    private String apellidos;
    private String role;
    private String password;

    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy="empleado", cascade = CascadeType.ALL)
    private Set<Trayecto> trayectos = new HashSet<>();

    public Empleado() {}

    public Empleado(String dni, String nombre, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Trayecto> getTrayectos() {
        return new HashSet<Trayecto>(trayectos);

    }

    public void setTrayectos(Set<Trayecto> trayectos) {
        this.trayectos = trayectos;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void iniciarTrayecto(Vehiculo vehiculo) {
        if(!vehiculo.isEstadoVehiculo()){
            throw new IllegalStateException("El vehículo seleccionado tiene un trayecto en curso");
        }
        trayectos.add(new Trayecto(this, vehiculo));
    }

    public void addTrayecto(Trayecto trayecto) {
        trayectos.add(trayecto);
    }
}
