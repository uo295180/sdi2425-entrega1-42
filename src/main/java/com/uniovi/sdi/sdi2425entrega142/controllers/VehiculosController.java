package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import com.uniovi.sdi.sdi2425entrega142.validators.VehiculosValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    VehiculosValidator vehiculosValidator;

    private final VehiculosService vehiculosService;
    @Autowired
    private TrayectosService trayectosService;

    public VehiculosController(VehiculosService vehiculosService) {
        this.vehiculosService = vehiculosService;
    }

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

    @RequestMapping("/vehiculo/list/update")
    public String updateList(Model model, Pageable pageable) {
        Page<Vehiculo> vehiculos = vehiculosService.getVehiculosDisponibles(pageable);
        model.addAttribute("vehiculosList", vehiculos.getContent());
        model.addAttribute("page", vehiculos);
        return "vehiculo/list/vehiculosTable::vehiculosTable";
    }


    @RequestMapping("/vehiculo/listAll")
    public String getTrayectos(@RequestParam(defaultValue = "0", required = false) int page,
                               Model model, Pageable pageable) {

        Page<Vehiculo> vehiculos = vehiculosService.getVehiculos(pageable);


        // Pasar los datos al modelo
        model.addAttribute("vehiculosList", vehiculos.getContent());
        model.addAttribute("page", vehiculos);

        return "vehiculo/listAll"; // Aquí es donde se carga la vista completa
    }

    @RequestMapping("/vehiculo/trayectos/{matricula}")
    public String updateList(@PathVariable(required = false) String matricula,
                             @RequestParam(defaultValue = "0") int page,
                             Model model, Pageable pageable) {

        Vehiculo vehiculo = vehiculosService.getVehiculoByMatricula(matricula); // Busca el vehículo por matrícula
        Page<Trayecto> trayectosPage = trayectosService.getTrayectosByVehiculo(vehiculo.getId(), pageable); // Paginación de trayectos

        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("trayectosList", trayectosPage.getContent());
        model.addAttribute("page", trayectosPage);
        model.addAttribute("totalTrayectos", trayectosPage.getTotalElements());
        return "vehiculo/trayectos";
    }
}
