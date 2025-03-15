package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Incidencia;
import com.uniovi.sdi.sdi2425entrega142.services.IncidenciasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IncidenciasController {
    @Autowired
    private IncidenciasService incidenciasService;

    @RequestMapping("/incidencia/list")
    public String getIncidenciasList(Model incidenciaModel) {
        incidenciaModel.addAttribute("incidenciasList", incidenciasService.getIncidencias());
        return "incidencia/list";
    }
    @RequestMapping(value = "/incidencia/add")
    public String getIncidencia(Model professorModel) {
        professorModel.addAttribute("incidencia", new Incidencia());
        return "incidencia/add";
    }
    @RequestMapping(value = "/incidencia/add", method = RequestMethod.POST)
    public String setIncidencia(Incidencia incidencia) {
        incidenciasService.addIncidencia(incidencia);
        return "redirect:/incidencia/list";
    }
}