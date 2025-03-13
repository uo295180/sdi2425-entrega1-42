package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Repostaje;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.RepostajesService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final TrayectosService trayectosService;

    public RepostajeController(VehiculosService vehiculosService, RepostajesService repostajesService,
                               EmpleadosService empleadosService, TrayectosService trayectosService) {
        this.vehiculosService = vehiculosService;
        this.repostajesService = repostajesService;
        this.empleadosService = empleadosService;
        this.trayectosService = trayectosService;
    }

     @RequestMapping("repostajes/add")
    public String getAddRepostaje(Model model, Principal principal) {
        String dni = principal.getName();
        Optional<Empleado> opEmpl = empleadosService.findEmpleadoByDni(dni);
        if (!opEmpl.isPresent()) {
            throw new IllegalStateException("No existe el empleado con el dni " + dni);
        }
        Empleado emp = opEmpl.get();
        Optional<Trayecto> opTrayecto = emp.getTrayectos().stream().filter(t -> t.isEstadoTrayecto()).findFirst();
        if(!opTrayecto.isPresent()) {
            throw new IllegalStateException("No existe el trayecto con el dni " + dni);
        }
        Vehiculo vehiculo = opTrayecto.get().getVehiculo();
        model.addAttribute("vehiculo", vehiculo);
        return "repostajes/add";
    }

    @RequestMapping(value="repostajes/add", method = RequestMethod.POST)
    public String addRepostaje(@ModelAttribute Repostaje repostaje,
                               @RequestParam String matricula, Model model) {
        Vehiculo vehiculo = vehiculosService.getVehiculoByMatricula(matricula);
        if(repostaje.isRepostajeCompleto()) {
            repostaje.setCantidadRepostada(vehiculo.getCantidadMaximaTanque()
                    - vehiculo.getCantidadTanqueTrasGasto(repostaje.getOdometro()));
        }
        repostaje.setVehiculo(vehiculo);
        repostaje.setFechaHoraRepostaje(new Timestamp(System.currentTimeMillis())); // or other defaults you may want
        repostaje.calculatePrecioTotal();

        repostajesService.addRepostaje(repostaje);

        model.addAttribute("repostaje", repostaje);
        return "repostajes/list";
    }

}
