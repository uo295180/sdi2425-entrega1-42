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
import org.springframework.validation.annotation.Validated;
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
        Page<Trayecto> trayectos;
        trayectos = trayectosService.getTrayectosDelEmpleado(pageable, empleado);
        model.addAttribute("trayectosList", trayectos.getContent());
        model.addAttribute("page", trayectos);
        return "trayecto/list";
    }

    @RequestMapping(value="/trayecto/add", method= RequestMethod.POST)
    public String setTrayecto(@Validated Trayecto trayecto, BindingResult result) {
        addTrayectoValidator.validate(trayecto, result);
        if(result.hasErrors()) {
            return "trayecto/add";
        }
        trayectosService.addTrayecto(trayecto);
        return "redirect:/trayecto/list";
    }

    @RequestMapping(value="/trayecto/add", method=RequestMethod.GET)
    public String setTrayecto(Model model) {
        model.addAttribute("trayecto", new Trayecto());
        return "trayecto/add";
    }

    @RequestMapping(value="/trayecto/add")
    public String getTrayecto(Model model, Pageable pageable) {
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
        return "fragments/trayectosTable::trayectosTable";
    }
}
