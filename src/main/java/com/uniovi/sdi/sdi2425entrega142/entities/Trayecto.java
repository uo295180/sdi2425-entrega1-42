package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;

@Entity
@Table(name = "Trayectos" )
public class Trayecto {

    @Id
    @GeneratedValue
    private Long id;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Empleado empleado;
}
