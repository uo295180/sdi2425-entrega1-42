package com.uniovi.sdi.sdi2425entrega142.entities;

import javax.persistence.*;

@Entity
@Table(name="Incidencias")
public class Incidencia {

    @Id
    @GeneratedValue
    private Long id;
}
