package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import com.uniovi.sdi.sdi2425entrega142.validators.AddTrayectoValidator;
import com.uniovi.sdi.sdi2425entrega142.validators.EditTrayectoValidator;
import com.uniovi.sdi.sdi2425entrega142.validators.EndTrayectoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class TrayectosController {

    private final TrayectosService trayectosService;
    private final EmpleadosService empleadosService;
    private final VehiculosService vehiculosService;
    private final AddTrayectoValidator addTrayectoValidator;
    private final EditTrayectoValidator editTrayectoValidator;
    private final EndTrayectoValidator endTrayectoValidator;

    public TrayectosController(TrayectosService trayectosService, EmpleadosService empleadosService,
                               VehiculosService vehiculosService, AddTrayectoValidator addTrayectoValidator,
                               EditTrayectoValidator editTrayectoValidator, EndTrayectoValidator endTrayectoValidator) {
        this.trayectosService = trayectosService;
        this.empleadosService = empleadosService;
        this.vehiculosService = vehiculosService;
        this.addTrayectoValidator = addTrayectoValidator;
        this.editTrayectoValidator = editTrayectoValidator;
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
    public String setTrayecto(@ModelAttribute Trayecto trayecto, @RequestParam("vehiculoMatricula") String matricula,
                              BindingResult result, Principal principal) {
        String dni = principal.getName();
        Empleado empleado = empleadosService.getByDni(dni);
        if (empleado == null) {
            return "redirect:/login";
        }
        trayecto.setEmpleado(empleado);

        // Obtener el objeto Vehículo a partir de la matrícula
        Vehiculo v = vehiculosService.getVehiculoByMatricula(matricula);
        trayecto.setVehiculo(v);

        addTrayectoValidator.validate(trayecto, result);
        if (result.hasErrors()) {
            return "trayecto/add";
        }

        v.setEstadoVehiculo(true);
        v.addTrayecto(trayecto);
        empleado.addTrayecto(trayecto);
        trayecto.setEstadoTrayecto(true);
        trayectosService.addTrayecto(trayecto);
        return "redirect:/trayecto/list";
    }

    @RequestMapping(value="/trayecto/add")
    public String getTrayecto(Model model, Pageable pageable) {
        model.addAttribute("trayecto", new Trayecto());
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
        return "redirect:/vehiculo/trayectos/"+vehiculo.getMatricula();
    }

    @RequestMapping("/trayecto/edit")
    public String selectVehiculo(Model model) {
        // Obtener solo los vehículos con trayectos finalizados,
        List<Vehiculo> vehiculosConTrayectosFinalizadosList = vehiculosService.findVehiculosConTrayectosFinalizados();
        model.addAttribute("vehiculosConTrayectosFinalizadosList", vehiculosConTrayectosFinalizadosList);
        return "trayecto/editSelectVehiculo";
    }

    @RequestMapping(value = "/trayecto/editSelectVehiculo", method = RequestMethod.POST)
    public String selectVehiculo(@RequestParam("vehiculoMatricula") String matricula, Model model) {
        // Buscar los trayectos finalizados del vehículo
        Vehiculo v = vehiculosService.getVehiculoByMatricula(matricula);
        List<Trayecto> trayectosFinalizados = trayectosService.findTrayectosFinalizadosPorVehiculo(v.getId());
        model.addAttribute("trayectosFinalizados", trayectosFinalizados);
        return "trayecto/editSelectTrayecto";
    }

    @RequestMapping("/trayecto/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Trayecto trayecto = trayectosService.getTrayecto(id);
        // Si está en curso, no permitimos editar
        if (trayecto.isEstadoTrayecto()) {
            return "redirect:/trayecto/edit";
        }
        model.addAttribute("trayecto", trayecto);
        return "trayecto/edit";
    }

    @RequestMapping(value="/trayecto/edit/{id}",  method = RequestMethod.POST)
    public String editForm(@PathVariable Long id, @Validated @ModelAttribute("trayecto") Trayecto trayecto,
                           BindingResult result, Model model) {
        editTrayectoValidator.validate(trayecto, result);
        if (result.hasErrors()) {
            return "trayecto/edit";
        }

        // Recuperamos el trayecto original y lo actualizamos
        Trayecto trayectoOriginal = trayectosService.getTrayecto(id);
        if (trayectoOriginal.isEstadoTrayecto()) {
            return "redirect:/trayecto/edit";
        }
        // Actualizamos campos
        trayectoOriginal.setFechaInicioTrayecto(trayecto.getFechaInicioTrayecto());
        trayectoOriginal.setFechaFinTrayecto(trayecto.getFechaFinTrayecto());
        trayectoOriginal.setOdometroInicio(trayecto.getOdometroInicio());
        trayectoOriginal.setOdometroFin(trayecto.getOdometroFin());
        trayectoOriginal.setObservaciones(trayecto.getObservaciones());
        trayectosService.addTrayecto(trayectoOriginal);

        // Redirigimos a la lista de trayectos
        return "redirect:/trayecto/list";
    }
}
