package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Repostaje;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.RepostajesService;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import com.uniovi.sdi.sdi2425entrega142.validators.RepostajesValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Optional;

@Controller
public class RepostajeController {

    private final VehiculosService vehiculosService;
    private final RepostajesService repostajesService;
    private final EmpleadosService empleadosService;

    private final RepostajesValidator repostajesValidator;

    public RepostajeController(VehiculosService vehiculosService, RepostajesService repostajesService,
                               EmpleadosService empleadosService, RepostajesValidator repostajesValidator) {
        this.vehiculosService = vehiculosService;
        this.repostajesService = repostajesService;
        this.empleadosService = empleadosService;
        this.repostajesValidator = repostajesValidator;
    }

     @RequestMapping("repostajes/add")
    public String getAddRepostaje(Model model, Principal principal) {
        String dni = principal.getName();
        Optional<Empleado> opEmpl = empleadosService.findEmpleadoByDni(dni);
        if (opEmpl.isEmpty()) {
            throw new IllegalStateException("No existe el empleado con el dni " + dni);
        }
        Empleado emp = opEmpl.get();
        Optional<Trayecto> opTrayecto = emp.getTrayectos().stream().filter(t -> t.isEstadoTrayecto()).findFirst();
        if(opTrayecto.isEmpty()) {
            return "redirect:/home";
        }
        Vehiculo vehiculo = opTrayecto.get().getVehiculo();
        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("repostaje", new Repostaje());
        return "repostajes/add";
    }

    @RequestMapping(value="repostajes/add", method = RequestMethod.POST)
    public String addRepostaje(@Validated Repostaje repostaje,
                               @RequestParam String matricula, BindingResult result, Model model, Principal principal) {
        Vehiculo vehiculo = vehiculosService.getVehiculoByMatricula(matricula);
        repostaje.setVehiculo(vehiculo);
        repostajesValidator.validate(repostaje, result);
        if (result.hasErrors()) {
            String dni = principal.getName();
            Optional<Empleado> opEmpl = empleadosService.findEmpleadoByDni(dni);
            if (opEmpl.isEmpty()) {
                throw new IllegalStateException("No existe el empleado con el dni " + dni);
            }
            Empleado emp = opEmpl.get();
            Optional<Trayecto> opTrayecto = emp.getTrayectos().stream().filter(t -> t.isEstadoTrayecto()).findFirst();
            if(opTrayecto.isEmpty()) {
                return "redirect:/home";
            }
            Vehiculo ve = opTrayecto.get().getVehiculo();
            model.addAttribute("vehiculo", ve);
            return "repostajes/add";
        }
        if(repostaje.isRepostajeCompleto()) {
            repostaje.setCantidadRepostada(vehiculo.getCantidadMaximaTanque()
                    - vehiculo.getCantidadTanqueTrasGasto(repostaje.getOdometro()));
        }
        vehiculo.respostar(repostaje);
        repostaje.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis())); // or other defaults you may want
        repostaje.calculatePrecioTotal();

        repostajesService.addRepostaje(repostaje);
        vehiculosService.addVehiculo(vehiculo);

        model.addAttribute("repostaje", repostaje);
        return "redirect:/vehiculo/repostajes/"+vehiculo.getMatricula();
    }
}
