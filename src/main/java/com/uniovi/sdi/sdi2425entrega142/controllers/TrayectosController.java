package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import com.uniovi.sdi.sdi2425entrega142.validators.AddTrayectoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class TrayectosController {

    private final TrayectosService trayectosService;
    private final EmpleadosService empleadosService;
    private final VehiculosService vehiculosService;
    private final AddTrayectoValidator addTrayectoValidator;

    public TrayectosController(TrayectosService trayectosService, EmpleadosService empleadosService, VehiculosService vehiculosService, AddTrayectoValidator addTrayectoValidator) {
        this.trayectosService = trayectosService;
        this.empleadosService = empleadosService;
        this.vehiculosService = vehiculosService;
        this.addTrayectoValidator = addTrayectoValidator;
    }

    @RequestMapping("/trayecto/list")
    public String getList(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName(); // DNI es el "name" de la autenticación
        Empleado empleado = empleadosService.getEmpleadoByDni(dni);
        Page<Trayecto> trayectos = trayectosService.getTrayectosDelEmpleado(pageable, empleado);
        model.addAttribute("trayectosList", trayectos.getContent());
        model.addAttribute("page", trayectos);
        return "trayecto/list";
    }

    @RequestMapping(value="/trayecto/add", method=RequestMethod.POST)
    public String setTrayecto(@ModelAttribute Trayecto trayecto, BindingResult result, Principal principal) {
        String dni = principal.getName(); // El empleado que registra el trayecto es el empleado en sesión
        Empleado empleado = empleadosService.getEmpleadoByDni(dni);
        if (empleado == null) { // Solo lanzará error si la sesión es inválida (si algún problema ocurre)
            return "redirect:/login";
        }
        trayecto.setEmpleado(empleado);

        // Verifica que el vehículo se esté recibiendo y asigna el vehículo correspondiente
        if (trayecto.getVehiculo() != null) {
            Vehiculo vehiculo = vehiculosService.getVehiculo(trayecto.getVehiculo().getId());
            trayecto.setVehiculo(vehiculo); // Asigna el vehículo correspondiente
        }

        addTrayectoValidator.validate(trayecto, result);
        if (result.hasErrors()) {
            return "trayecto/add";
        }
        trayectosService.addTrayecto(trayecto);
        return "redirect:/trayecto/list";
    }


    @RequestMapping(value="/trayecto/add", method=RequestMethod.GET)
    public String getTrayecto(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName(); // DNI es el "name" de la autenticación
        Empleado empleado = empleadosService.getEmpleadoByDni(dni);
        Trayecto trayecto = new Trayecto();
        trayecto.setEmpleado(empleado);

        model.addAttribute("trayecto", trayecto);
        model.addAttribute("vehiculosList", vehiculosService.getVehiculosDisponibles(pageable));
        return "trayecto/add";
    }

    @RequestMapping("/trayecto/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        Empleado empleado = empleadosService.getEmpleadoByDni(dni);
        Page<Trayecto> trayectos = trayectosService.getTrayectosDelEmpleado(pageable, empleado);
        model.addAttribute("trayectosList", trayectos.getContent());
        model.addAttribute("page", trayectos);
        return "trayecto/list/trayectosTable::trayectosTable";
    }
}
