package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import com.uniovi.sdi.sdi2425entrega142.validators.AddTrayectoValidator;
import com.uniovi.sdi.sdi2425entrega142.validators.EndTrayectoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class TrayectosController {

    private final TrayectosService trayectosService;
    private final EmpleadosService empleadosService;
    private final VehiculosService vehiculosService;
    private final AddTrayectoValidator addTrayectoValidator;
    private final EndTrayectoValidator endTrayectoValidator;

    public TrayectosController(TrayectosService trayectosService, EmpleadosService empleadosService,
                               VehiculosService vehiculosService, AddTrayectoValidator addTrayectoValidator,
                               EndTrayectoValidator endTrayectoValidator) {
        this.trayectosService = trayectosService;
        this.empleadosService = empleadosService;
        this.vehiculosService = vehiculosService;
        this.addTrayectoValidator = addTrayectoValidator;
        this.endTrayectoValidator = endTrayectoValidator;
    }

    @RequestMapping("/trayecto/list")
    public String getList(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName(); // DNI es el "name" de la autenticación
        Empleado empleado = empleadosService.getByDni(dni);
        Page<Trayecto> trayectos = trayectosService.getTrayectosDelEmpleado(pageable, empleado);
        model.addAttribute("trayectosList", trayectos.getContent());
        model.addAttribute("page", trayectos);
        return "trayecto/list";
    }

    @RequestMapping(value="/trayecto/add", method=RequestMethod.POST)
    public String setTrayecto(@ModelAttribute Trayecto trayecto, @RequestParam("vehiculo") Long vehiculoId,
                              BindingResult result, Principal principal) {
        String dni = principal.getName();
        Empleado empleado = empleadosService.getByDni(dni);
        if (empleado == null) {
            return "redirect:/login";
        }
        trayecto.setEmpleado(empleado);

        // Obtener el objeto Vehículo y asignarlo al Trayecto
        if (vehiculoId != null) {
            Vehiculo v = vehiculosService.getVehiculo(vehiculoId);
            trayecto.setVehiculo(v);
        }

        addTrayectoValidator.validate(trayecto, result);
        System.out.println(result.getAllErrors()); // Agregar esta línea para ver los errores en la consola
        if (result.hasErrors()) {
            return "trayecto/add";
        }

        trayectosService.addTrayecto(trayecto);
        return "redirect:/trayecto/list";
    }

    @RequestMapping(value="/trayecto/add", method=RequestMethod.GET)
    public String getTrayecto(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName(); // DNI es el "name" de la autenticación
        Empleado empleado = empleadosService.getByDni(dni);
        Trayecto trayecto = new Trayecto();
        trayecto.setEmpleado(empleado);

        model.addAttribute("trayecto", trayecto);
        model.addAttribute("vehiculosList", vehiculosService.getVehiculosDisponibles(pageable));
        return "trayecto/add";
    }

    @RequestMapping("/trayecto/list/update")
    public String updateList(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        Empleado empleado = empleadosService.getByDni(dni);
        Page<Trayecto> trayectos = trayectosService.getTrayectosDelEmpleado(pageable, empleado);
        model.addAttribute("trayectosList", trayectos.getContent());
        model.addAttribute("page", trayectos);
        return "trayecto/list/trayectosTable::trayectosTable";
    }

    @RequestMapping("/trayecto/end")
    public String endTrayecto(Model model, Pageable pageable, Principal principal) {
        String dni = principal.getName();
        Empleado empleado = empleadosService.getByDni(dni);
        Optional<Trayecto> opTrayecto = trayectosService.findTrayectoActivoByUser(empleado);
        if (!opTrayecto.isPresent()) {
            return "redirect:/home";
        }
        Trayecto trayecto = opTrayecto.get();
        model.addAttribute("trayecto", trayecto);
        Vehiculo vehiculo = vehiculosService.getVehiculo(trayecto.getVehiculo().getId());
        model.addAttribute("vehiculo", vehiculo);
        return "trayecto/end";
    }

    @RequestMapping(value="/trayecto/end", method = RequestMethod.POST)
    public String endTrayectoPost(@Validated Trayecto trayecto, BindingResult result, Principal principal, Model model) {
        String dni = principal.getName();
        Empleado empleado = empleadosService.getByDni(dni);
        Optional<Trayecto> opTrayecto = trayectosService.findTrayectoActivoByUser(empleado);
        Trayecto originalTrayecto = opTrayecto.get();
        Vehiculo vehiculo = vehiculosService.getVehiculo(originalTrayecto.getVehiculo().getId());
        trayecto.setVehiculo(vehiculo);
        endTrayectoValidator.validate(trayecto, result);
        if (result.hasErrors()) {
            trayecto.setFechaInicioTrayecto(originalTrayecto.getFechaInicioTrayecto());
            model.addAttribute("vehiculo", vehiculo);
            return "trayecto/end";
        }
        originalTrayecto.endTrayecto(trayecto.getOdometroFin(), trayecto.getObservaciones());
        trayectosService.addTrayecto(originalTrayecto);
        vehiculosService.addVehiculo(vehiculo);
        return "redirect:/vehiculo/trayectos/"+vehiculo.getId();
    }
}
