package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VehiculosController {

    private final VehiculosService vehiculosService;

    public VehiculosController(VehiculosService vehiculosService) {
        this.vehiculosService = vehiculosService;
    }

    @RequestMapping("/vehiculo/list")
    public String getList(Model model, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculosService.getVehiculosDisponibles(pageable);
        model.addAttribute("vehiculosList", vehiculos.getContent());
        model.addAttribute("page", vehiculos);
        return "vehiculo/list";
    }

    @RequestMapping(value="/vehiculo/add", method= RequestMethod.POST)
    public String setVehiculo(@Validated Vehiculo vehiculo, BindingResult result) {
        vehiculosService.addVehiculo(vehiculo);
        return "redirect:/vehiculo/list";
    }

    @RequestMapping(value="/vehiculo/add", method=RequestMethod.GET)
    public String setVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculo/add";
    }

    @RequestMapping("/vehiculo/list/update")
    public String updateList(Model model, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculosService.getVehiculosDisponibles(pageable);
        model.addAttribute("vehiculosList", vehiculos.getContent());
        model.addAttribute("page", vehiculos);
        return "fragments/vehiculosTable::vehiculosTable";
    }
}
