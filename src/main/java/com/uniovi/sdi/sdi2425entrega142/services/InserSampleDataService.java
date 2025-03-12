package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.EmpleadosRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InserSampleDataService {

    private final EmpleadosService empleadosService;
    private final VehiculosService vehiculosService;
    private final RolesService rolesService;

    public InserSampleDataService(EmpleadosService empleadosService, RolesService rolesService,
                                  VehiculosService vehiculosService) {
        this.empleadosService = empleadosService;
        this.vehiculosService = vehiculosService;
        this.rolesService = rolesService;
    }

    @PostConstruct
    public void init() {
        Empleado empleado1 = new Empleado("12345678A", "Mario", "Orviz");
        empleado1.setPassword("123456");
        empleado1.setRole(rolesService.getRoles()[1]);

        Empleado empleado2 = new Empleado("12345678B", "Fernando", "Cachón");
        empleado2.setPassword("123456");
        empleado2.setRole(rolesService.getRoles()[1]);

        Empleado empleado3 = new Empleado("12345678C", "Vicente", "Megido");
        empleado3.setPassword("123456");
        empleado3.setRole(rolesService.getRoles()[0]);

        Empleado empleado4 = new Empleado("12345678D", "Darío", "Corbeira");
        empleado4.setPassword("123456");
        empleado4.setRole(rolesService.getRoles()[0]);

        Empleado administrador = new Empleado("12345678Z", "Admin", "Admin");
        administrador.setPassword("@Dm1n1str@D0r");
        administrador.setRole(rolesService.getRoles()[1]);

        Vehiculo v1 = new Vehiculo(1000, 50, "Audi", "1234AAA",
                "A5", "pppppppppppppppaa", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo v2 = new Vehiculo(1000, 50, "BMW", "1234ZZZ",
                "M3", "bbbbbbbbbbbbbbbbb", Vehiculo.TipoCombustible.MICROHIBRIDO);
        Vehiculo v3 = new Vehiculo(1000, 50, "Seat", "1234WWW",
                "M3", "aaaaaaaaaaaaaaaaa", Vehiculo.TipoCombustible.HIBRIDO);
        Vehiculo v4 = new Vehiculo(1000, 50, "Ford", "1234LLL",
                "M3", "aaaaaaaaaaaaaaaff", Vehiculo.TipoCombustible.HIBRIDO);

        empleadosService.addEmpleado(empleado1);
        empleadosService.addEmpleado(empleado2);
        empleadosService.addEmpleado(empleado3);
        empleadosService.addEmpleado(empleado4);
        empleadosService.addEmpleado(administrador);

        vehiculosService.addVehiculo(v1);
        vehiculosService.addVehiculo(v2);
        vehiculosService.addVehiculo(v3);
        vehiculosService.addVehiculo(v4);
    }
}
