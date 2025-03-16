package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Incidencia;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.IncidenciasService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

@Controller
public class IncidenciasController {
    @Autowired
    private IncidenciasService incidenciasService;
    @Autowired
    private EmpleadosService empleadosService;
    @Autowired
    private TrayectosService trayectosService;

    @RequestMapping("/incidencia/list")
    public String getIncidenciasList(Model incidenciaModel) {
        incidenciaModel.addAttribute("incidenciasList", incidenciasService.getIncidencias());
        return "incidencia/list";
    }
    @RequestMapping(value = "/incidencia/add")
    public String getIncidencia(Model incidenciaModel) {
        incidenciaModel.addAttribute("incidencia", new Incidencia());
        return "incidencia/add";
    }
    @RequestMapping(value = "/incidencia/add", method = RequestMethod.POST)
    public String setIncidencia(Incidencia incidencia, Principal principal) {
        Trayecto trayecto = null;
        if(principal != null) {
            String dni = principal.getName();
            Empleado empleado = empleadosService.getByDni(dni);
            Optional<Trayecto> opTrayecto = trayectosService.findTrayectoActivoByUser(empleado);
            trayecto = opTrayecto.get();
        }
        incidencia.setFechaHoraIncidencia(new Timestamp(System.currentTimeMillis()));
        incidencia.setTrayecto(trayecto);
        incidenciasService.addIncidencia(incidencia);
        return "redirect:/incidencia/list";
    }
    @RequestMapping("/incidencia/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        Incidencia incidencia = incidenciasService.getIncidencia(id);
        incidencia.marcarLeida();
        incidenciasService.addIncidencia(incidencia);
        model.addAttribute("incidencia", incidenciasService.getIncidencia(id));
        return "incidencia/details";
    }
    @RequestMapping(value = "/incidencias/details/{id}/en-proceso", method = RequestMethod.GET)
    public String setEstadoEnProceso(@PathVariable Long id, Model model) {
        Incidencia incidencia = incidenciasService.getIncidencia(id);
        incidencia.setEstado(Incidencia.EstadoIncidencia.EN_PROCESO);
        incidenciasService.addIncidencia(incidencia);
        model.addAttribute("incidencia", incidencia);
        return "incidencia/details :: estadoIncidencia";
    }

    @RequestMapping(value = "/incidencias/details/{id}/resuelta", method = RequestMethod.GET)
    public String setEstadoResuelta(@PathVariable Long id, Model model) {
        Incidencia incidencia = incidenciasService.getIncidencia(id);
        incidencia.setEstado(Incidencia.EstadoIncidencia.RESUELTA);
        incidenciasService.addIncidencia(incidencia);
        model.addAttribute("incidencia", incidencia);
        return "incidencia/details :: estadoIncidencia";
    }
}
