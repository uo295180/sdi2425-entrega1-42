package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class TrayectosController {

    private final TrayectosService trayectosService;
    private final EmpleadosService empleadosService;

    public TrayectosController(TrayectosService trayectosService, EmpleadosService empleadosService) {
        this.trayectosService = trayectosService;
        this.empleadosService = empleadosService;
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
        model.addAttribute("empleadosList", empleadosService.getEmpleados(pageable));
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
