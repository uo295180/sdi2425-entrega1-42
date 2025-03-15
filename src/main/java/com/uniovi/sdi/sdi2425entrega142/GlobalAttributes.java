package com.uniovi.sdi.sdi2425entrega142;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.TrayectosService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Optional;

@ControllerAdvice
public class GlobalAttributes {

    private final TrayectosService trayectosService;
    private final EmpleadosService empleadosService;

    public GlobalAttributes(TrayectosService trayectosService, EmpleadosService empleadosService) {
        this.trayectosService = trayectosService;
        this.empleadosService = empleadosService;
    }

    @ModelAttribute("tieneTrayectoEnCurso")
    public boolean tieneTrayectoEnCurso(Principal principal) {
        if(principal != null) {
            String dni = principal.getName();
            Empleado empleado = empleadosService.getByDni(dni);
            Optional<Trayecto> opTrayecto = trayectosService.findTrayectoActivoByUser(empleado);
            return opTrayecto.isPresent();
        }
        return false;
    }
}
