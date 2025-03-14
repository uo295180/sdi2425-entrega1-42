package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.repository.EmpleadosRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InsertSampleDataService {

    private final EmpleadosService empleadosService;
    private final VehiculosService vehiculosService;
    private final RolesService rolesService;
    private final TrayectosService trayectosService;

    public InsertSampleDataService(EmpleadosService empleadosService, VehiculosService vehiculosService, RolesService rolesService, TrayectosService trayectosService) {
        this.empleadosService = empleadosService;
        this.vehiculosService = vehiculosService;
        this.rolesService = rolesService;
        this.trayectosService = trayectosService;
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

        empleadosService.addEmpleado(empleado1);
        empleadosService.addEmpleado(empleado2);
        empleadosService.addEmpleado(empleado3);
        empleadosService.addEmpleado(empleado4);
        empleadosService.addEmpleado(administrador);

        Vehiculo v1 = new Vehiculo(1000, 50, "Audi", "1234AAA",
                "A5", "pppppppppppppppaa", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo v2 = new Vehiculo(1000, 50, "BMW", "1234ZZZ",
                "M3", "bbbbbbbbbbbbbbbbb", Vehiculo.TipoCombustible.MICROHIBRIDO);
        Vehiculo v3 = new Vehiculo(1000, 50, "Seat", "1234WWW",
                "M3", "aaaaaaaaaaaaaaaaa", Vehiculo.TipoCombustible.HIBRIDO);
        Vehiculo v4 = new Vehiculo(1000, 50, "Ford", "1234LLL",
                "M3", "aaaaaaaaaaaaaaaff", Vehiculo.TipoCombustible.HIBRIDO);

        vehiculosService.addVehiculo(v1);
        vehiculosService.addVehiculo(v2);
        vehiculosService.addVehiculo(v3);
        vehiculosService.addVehiculo(v4);

        Trayecto t1 = new Trayecto(empleado1, v1);
        v1.setEstadoVehiculo(true);
        v1.addTrayecto(t1);
        empleado1.addTrayecto(t1);

        trayectosService.addTrayecto(t1);


        Vehiculo vehiculo1 = new Vehiculo(100.0, 6.5, "Renault", "1234BBB", "Captur", "123456789abcdefgh", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo vehiculo2 = new Vehiculo(124.0, 7.4, "Mercedes", "0000BCD", "GLE", "000000000abcdefgh", Vehiculo.TipoCombustible.HIBRIDO);
        Vehiculo vehiculo3 = new Vehiculo(60.0, 2.5, "Tesla", "1234CCC", "Model Y", "777777777abcdefgh", Vehiculo.TipoCombustible.ELECTRICO);
        Vehiculo vehiculo4 = new Vehiculo(58.0, 4.5, "Citroen", "1111ZZZ", "C15", "111111111abcdefgh", Vehiculo.TipoCombustible.GASOLINA);
        Vehiculo vehiculo5 = new Vehiculo(80.0, 8.5, "Ford", "4444KKK", "Mustang GT", "444400000kkkkkkkk", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo vehiculo6 = new Vehiculo(63.0, 3.8, "Dacia", "1111CCZ", "Sandero", "111211111abcdefgh", Vehiculo.TipoCombustible.GLP);
        Vehiculo vehiculo7 = new Vehiculo(108.0, 6.0, "Jeep", "1331ZDZ", "Wrangler", "111144111abcdefgh", Vehiculo.TipoCombustible.MICROHIBRIDO);

        vehiculosService.addVehiculo(vehiculo1);
        vehiculosService.addVehiculo(vehiculo2);
        vehiculosService.addVehiculo(vehiculo3);
        vehiculosService.addVehiculo(vehiculo4);
        vehiculosService.addVehiculo(vehiculo5);
        vehiculosService.addVehiculo(vehiculo6);
        vehiculosService.addVehiculo(vehiculo7);

        Trayecto trayecto1 = new Trayecto(empleado3, vehiculo4);
        vehiculo4.setEstadoVehiculo(true);
        vehiculo4.addTrayecto(trayecto1);
        empleado3.addTrayecto(trayecto1);

        trayectosService.addTrayecto(trayecto1);


        Trayecto prueba1 = new Trayecto(empleado2, vehiculo3);
        vehiculo3.setEstadoVehiculo(true);
        vehiculo3.addTrayecto(prueba1);
        empleado2.addTrayecto(prueba1);

        prueba1.endTrayecto(100, "Observaciones");
        trayectosService.addTrayecto(prueba1);
        vehiculosService.addVehiculo(vehiculo3);
        Trayecto prueba2 = new Trayecto(empleado2, vehiculo3);
        vehiculo3.setEstadoVehiculo(true);
        vehiculo3.addTrayecto(prueba2);
        empleado2.addTrayecto(prueba2);
        trayectosService.addTrayecto(prueba2);





        // Dummy data for functional tests of requisites 11, 12

        Empleado empleado5 = new Empleado("12345678F", "Vicente", "Megido");
        empleado5.setPassword("123456");
        empleado5.setRole(rolesService.getRoles()[0]);

        Empleado empleado6 = new Empleado("12345678G", "Darío", "Corbeira");
        empleado6.setPassword("123456");
        empleado6.setRole(rolesService.getRoles()[0]);

        Empleado empleadoSinTrayecto = new Empleado("12345678E", "Sin Trayectos", "No trayectos");
        empleadoSinTrayecto.setPassword("123456");
        empleadoSinTrayecto.setRole(rolesService.getRoles()[0]);

        empleadosService.addEmpleado(empleadoSinTrayecto);
        empleadosService.addEmpleado(empleado5);
        empleadosService.addEmpleado(empleado6);

        Vehiculo vehiculo8 = new Vehiculo(100.0, 6.5, "Renault", "4321AAA", "Captur", "123456789abcdefgh", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo vehiculo9 = new Vehiculo(124.0, 7.4, "Mercedes", "4321BBB", "GLE", "000000000abcdefgh", Vehiculo.TipoCombustible.HIBRIDO);

        vehiculosService.addVehiculo(vehiculo8);
        vehiculosService.addVehiculo(vehiculo9);

        Trayecto trayecto2 = new Trayecto(empleado5, vehiculo8);
        vehiculo8.setEstadoVehiculo(true);
        vehiculo8.addTrayecto(trayecto2);
        empleado5.addTrayecto(trayecto2);

        trayectosService.addTrayecto(trayecto2);

        Trayecto trayecto3 = new Trayecto(empleado6, vehiculo9);
        vehiculo9.setEstadoVehiculo(true);
        vehiculo9.addTrayecto(trayecto3);
        empleado6.addTrayecto(trayecto3);

        trayectosService.addTrayecto(trayecto3);
    }
}
