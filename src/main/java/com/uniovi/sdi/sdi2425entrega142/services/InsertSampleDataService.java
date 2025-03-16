package com.uniovi.sdi.sdi2425entrega142.services;

import com.uniovi.sdi.sdi2425entrega142.entities.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;

@Service
public class InsertSampleDataService {

    private final EmpleadosService empleadosService;
    private final VehiculosService vehiculosService;
    private final RolesService rolesService;
    private final TrayectosService trayectosService;
    private final RepostajesService repostajesService;
    private final IncidenciasService incidenciasService;

    public InsertSampleDataService(EmpleadosService empleadosService, VehiculosService vehiculosService,
                                   RolesService rolesService, TrayectosService trayectosService,
                                   RepostajesService repostajesService, IncidenciasService incidenciasService) {
        this.empleadosService = empleadosService;
        this.vehiculosService = vehiculosService;
        this.rolesService = rolesService;
        this.trayectosService = trayectosService;
        this.repostajesService = repostajesService;
        this.incidenciasService = incidenciasService;
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

        // Creación de un coche con muchos trayectos para la prueba de el requisito 13

        Vehiculo vehiculo10 = new Vehiculo(1000.0, 7.4, "Citroen", "4321CCC", "GLE", "000000000abcdefgd", Vehiculo.TipoCombustible.HIBRIDO);
        vehiculosService.addVehiculo(vehiculo10);
        Empleado empleado7 = new Empleado("12345678H", "Empleado", "Trayectos");
        empleado7.setPassword("123456");
        empleado7.setRole(rolesService.getRoles()[0]);

        empleadosService.addEmpleado(empleado7);

        Trayecto t13_1 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_1);
        empleado7.addTrayecto(t13_1);

        t13_1.endTrayecto(10, "Observaciones");

        trayectosService.addTrayecto(t13_1);

        Trayecto t13_2 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_2);
        empleado7.addTrayecto(t13_2);

        t13_2.endTrayecto(20, "Observaciones");

        trayectosService.addTrayecto(t13_2);

        Trayecto t13_3 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_3);
        empleado7.addTrayecto(t13_3);

        t13_3.endTrayecto(30, "Observaciones");

        trayectosService.addTrayecto(t13_3);

        Trayecto t13_4 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_4);
        empleado7.addTrayecto(t13_4);

        t13_4.endTrayecto(40, "Observaciones");

        trayectosService.addTrayecto(t13_4);

        Trayecto t13_5 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_5);
        empleado7.addTrayecto(t13_5);

        t13_5.endTrayecto(50, "Observaciones");

        trayectosService.addTrayecto(t13_5);

        Trayecto t13_6 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_6);
        empleado7.addTrayecto(t13_6);

        t13_6.endTrayecto(60, "Observaciones");

        trayectosService.addTrayecto(t13_6);

        Trayecto t13_7 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_7);
        empleado7.addTrayecto(t13_7);

        t13_7.endTrayecto(70, "Observaciones");

        trayectosService.addTrayecto(t13_7);

        Trayecto t13_8 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_8);
        empleado7.addTrayecto(t13_8);

        t13_8.endTrayecto(80, "Observaciones");

        trayectosService.addTrayecto(t13_8);

        Trayecto t13_9 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_9);
        empleado7.addTrayecto(t13_9);

        t13_9.endTrayecto(90, "Observaciones");

        trayectosService.addTrayecto(t13_9);

        Trayecto t13_10 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_10);
        empleado7.addTrayecto(t13_10);

        t13_10.endTrayecto(100, "Observaciones");

        trayectosService.addTrayecto(t13_10);

        Trayecto t13_11 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_11);
        empleado7.addTrayecto(t13_11);

        t13_11.endTrayecto(110, "Observaciones");

        trayectosService.addTrayecto(t13_11);

        Trayecto t13_12 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_12);
        empleado7.addTrayecto(t13_12);

        t13_12.endTrayecto(120, "Observaciones");

        trayectosService.addTrayecto(t13_12);

        Trayecto t13_13 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_13);
        empleado7.addTrayecto(t13_13);

        t13_13.endTrayecto(130, "Observaciones");

        trayectosService.addTrayecto(t13_13);

        Trayecto t13_14 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_14);
        empleado7.addTrayecto(t13_14);

        Repostaje r9 = new Repostaje(48, "Estación prueba", "Observación 9", 133, 1.12);
        repostajesService.addRepostaje(r9);
        r9.setVehiculo(vehiculo10);
        vehiculo10.respostar(r9);
        r9.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r9.calculatePrecioTotal();

        Repostaje r10 = new Repostaje(49, "Estación prueba", "Observación 10", 134, 1.13);
        repostajesService.addRepostaje(r10);
        r10.setVehiculo(vehiculo10);
        vehiculo10.respostar(r10);
        r10.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r10.calculatePrecioTotal();

        Repostaje r11 = new Repostaje(50, "Estación prueba", "Observación 11", 135, 1.14);
        repostajesService.addRepostaje(r11);
        r11.setVehiculo(vehiculo10);
        vehiculo10.respostar(r11);
        r11.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r11.calculatePrecioTotal();

        Repostaje r12 = new Repostaje(51, "Estación prueba", "Observación 12", 136, 1.15);
        repostajesService.addRepostaje(r12);
        r12.setVehiculo(vehiculo10);
        vehiculo10.respostar(r12);
        r12.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r12.calculatePrecioTotal();

        Repostaje r13 = new Repostaje(52, "Estación prueba", "Observación 13", 137, 1.16);
        repostajesService.addRepostaje(r13);
        r13.setVehiculo(vehiculo10);
        vehiculo10.respostar(r13);
        r13.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r13.calculatePrecioTotal();

        Repostaje r14 = new Repostaje(53, "Estación prueba", "Observación 14", 138, 1.17);
        repostajesService.addRepostaje(r14);
        r14.setVehiculo(vehiculo10);
        vehiculo10.respostar(r14);
        r14.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r14.calculatePrecioTotal();

        Repostaje r15 = new Repostaje(53, "Estación prueba", "Observación 14", 139, 1.17);
        repostajesService.addRepostaje(r15);
        r15.setVehiculo(vehiculo10);
        vehiculo10.respostar(r15);
        r15.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r15.calculatePrecioTotal();

        t13_14.endTrayecto(140, "Observaciones");

        trayectosService.addTrayecto(t13_14);

        Trayecto t13_15 = new Trayecto(empleado7, vehiculo10);
        vehiculo10.setEstadoVehiculo(true);
        vehiculo10.addTrayecto(t13_15);
        empleado7.addTrayecto(t13_15);

        Repostaje r1 = new Repostaje(40, "Estación prueba", "Obsevación 1", 141, 1.04);
        repostajesService.addRepostaje(r1);

        r1.setVehiculo(vehiculo10);
        vehiculo10.respostar(r1);
        r1.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis())); // or other defaults you may want
        r1.calculatePrecioTotal();

        Repostaje r2 = new Repostaje(41, "Estación prueba", "Observación 2", 142, 1.05);
        repostajesService.addRepostaje(r2);
        r2.setVehiculo(vehiculo10);
        vehiculo10.respostar(r2);
        r2.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r2.calculatePrecioTotal();

        Repostaje r3 = new Repostaje(42, "Estación prueba", "Observación 3", 143, 1.06);
        repostajesService.addRepostaje(r3);
        r3.setVehiculo(vehiculo10);
        vehiculo10.respostar(r3);
        r3.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r3.calculatePrecioTotal();

        Repostaje r4 = new Repostaje(43, "Estación prueba", "Observación 4", 144, 1.07);
        repostajesService.addRepostaje(r4);
        r4.setVehiculo(vehiculo10);
        vehiculo10.respostar(r4);
        r4.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r4.calculatePrecioTotal();

        Repostaje r5 = new Repostaje(44, "Estación prueba", "Observación 5", 145, 1.08);
        repostajesService.addRepostaje(r5);
        r5.setVehiculo(vehiculo10);
        vehiculo10.respostar(r5);
        r5.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r5.calculatePrecioTotal();

        Repostaje r6 = new Repostaje(45, "Estación prueba", "Observación 6", 146, 1.09);
        repostajesService.addRepostaje(r6);
        r6.setVehiculo(vehiculo10);
        vehiculo10.respostar(r6);
        r6.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r6.calculatePrecioTotal();

        Repostaje r7 = new Repostaje(46, "Estación prueba", "Observación 7", 147, 1.10);
        repostajesService.addRepostaje(r7);
        r7.setVehiculo(vehiculo10);
        vehiculo10.respostar(r7);
        r7.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r7.calculatePrecioTotal();

        Repostaje r8 = new Repostaje(47, "Estación prueba", "Observación 8", 148, 1.11);
        repostajesService.addRepostaje(r8);
        r8.setVehiculo(vehiculo10);
        vehiculo10.respostar(r8);
        r8.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis()));
        r8.calculatePrecioTotal();

        t13_15.endTrayecto(150, "Observaciones");

        trayectosService.addTrayecto(t13_15);

        empleadosService.addEmpleado(empleado7);
        vehiculosService.addVehiculo(vehiculo10);

        Empleado empleado10 = new Empleado("99999998A", "NOMBRE", "CONADMIN");
        empleado10.setPassword("123456");
        empleado10.setRole(rolesService.getRoles()[1]);
        empleadosService.addEmpleado(empleado10);

        Empleado empleado11 = new Empleado("99999998B", "NOMBRE", "SINADMIN");
        empleado11.setPassword("123456");
        empleado11.setRole(rolesService.getRoles()[0]);
        empleadosService.addEmpleado(empleado11);

        // Test 24
        Vehiculo t24_v = new Vehiculo(104.0, 12.2, "Ferrari", "2404KMG", "F80", "V2I4C0E4N2T0E0M4G", Vehiculo.TipoCombustible.DIESEL);
        vehiculosService.addVehiculo(t24_v);
        Empleado t24_e = new Empleado("99999998C", "NOMBRE", "SINADMIN");
        t24_e.setPassword("123456");
        t24_e.setRole(rolesService.getRoles()[0]);
        empleadosService.addEmpleado(t24_e);
        Trayecto t24_t = new Trayecto(t24_e, t24_v);
        trayectosService.addTrayecto(t24_t);

        Incidencia i1 = new Incidencia(new Timestamp(System.currentTimeMillis()),"Rompio", "Reventó el capo", true, Incidencia.EstadoIncidencia.EN_PROCESO, "Compra un nuevo capo",t1);
        incidenciasService.addIncidencia(i1);

    }
}
