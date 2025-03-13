package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import com.uniovi.sdi.sdi2425entrega142.validators.VehiculosValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VehiculosController {
    @Autowired
    VehiculosService vehiculosService;
    @Autowired
    VehiculosValidator vehiculosValidator;

    @RequestMapping("/vehiculo/list")
    public String getVehiculosList(Pageable pageable, Model vehiculosModel) {
        Page<Vehiculo> vehiculos = vehiculosService.getVehiculos(pageable);
        vehiculosModel.addAttribute("vehiculosList", vehiculos.getContent());
        vehiculosModel.addAttribute("page", vehiculos);
        return "vehiculo/list";
    }
    @RequestMapping(value = "/vehiculo/add")
    public String getVehiculo(Model vehiculosModel) {
        vehiculosModel.addAttribute("vehiculo", new Vehiculo());
        return "vehiculo/add";
    }
    @RequestMapping(value = "/vehiculo/add", method = RequestMethod.POST)
    public String setVehiculo(@Validated Vehiculo vehiculo, BindingResult result, Model vehiculosModel) {
        vehiculosValidator.validate(vehiculo, result);
        if (result.hasErrors()) {
            vehiculosModel.addAttribute("vehiculo", vehiculo);
            return "/vehiculo/add";
        }
        vehiculosService.addVehiculo(vehiculo);
        return "redirect:/vehiculo/list";
    }
    @RequestMapping("/vehiculo/delete/{id}")
    public String deleteVehiculo(@PathVariable Long id) {
        vehiculosService.deleteVehiculo(id);
        return "redirect:/vehiculo/list";
    }
    @RequestMapping(value = "/vehiculo/delete", method = RequestMethod.POST)
    public String deleteMultipleVehiculos(@RequestParam("vehiculosSeleccionados") List<Long> vehiculosIds) {
        for (Long id : vehiculosIds) {
            vehiculosService.deleteVehiculo(id);
        }
        return "redirect:/vehiculo/list";
    }

}
