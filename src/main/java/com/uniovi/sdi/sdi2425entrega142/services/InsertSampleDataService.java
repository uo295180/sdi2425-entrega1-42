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
    private final RolesService rolesService;
    private final TrayectosService trayectosService;

    public InsertSampleDataService(EmpleadosService empleadosService, RolesService rolesService, TrayectosService trayectosService) {
        this.empleadosService = empleadosService;
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

        Vehiculo vehiculo1 = new Vehiculo(100.0, 6.5, "Renault", "1234ZZZ", "Captur", "123456789abcdefgh", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo vehiculo2 = new Vehiculo(124.0, 7.4, "Mercedes", "0000BCD", "GLE", "000000000abcdefgh", Vehiculo.TipoCombustible.HIBRIDO);
        Vehiculo vehiculo3 = new Vehiculo(60.0, 2.5, "Tesla", "1234LLL", "Model Y", "777777777abcdefgh", Vehiculo.TipoCombustible.ELECTRICO);
        Vehiculo vehiculo4 = new Vehiculo(80.0, 8.5, "Ford", "4444KKK", "Mustang GT", "444400000kkkkkkkk", Vehiculo.TipoCombustible.DIESEL);
        Vehiculo vehiculo5 = new Vehiculo(58.0, 4.5, "Citroen", "1111ZZZ", "C15", "111111111abcdefgh", Vehiculo.TipoCombustible.GASOLINA);

        Trayecto trayecto1 = new Trayecto(empleado3, vehiculo4);

        trayectosService.addTrayecto(trayecto1);
    }
}
