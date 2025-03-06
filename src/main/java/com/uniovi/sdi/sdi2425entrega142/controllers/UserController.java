package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.RolesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final EmpleadosService empleadosService;
    private final RolesService rolesService;

    public UserController(EmpleadosService empleadosService, RolesService rolesService) {
        this.empleadosService = empleadosService;
        this.rolesService = rolesService;
    }


    //Rest
    @RequestMapping("/empleado/list")
    public String getListado(Model model, @RequestParam(value = "searchText", required = false) String searchText) {

        List<Empleado> users;

        if (searchText != null && !searchText.isEmpty()) {
            // Si hay texto de búsqueda, usamos un servicio para buscar los usuarios por nombre o apellidos.
            users = empleadosService.searchEmpleados(searchText);
        } else {
            // Si no hay búsqueda, mostramos todos los usuarios
            users = empleadosService.getEmpleados();
        }

        model.addAttribute("empleadosList", users);
        return "empleado/list";
    }


    @RequestMapping(value = "/empleado/add")
    public String getUser(Model model) {
        model.addAttribute("rolesList", rolesService.getRoles());
        return "empleado/add";
    }
    @RequestMapping(value = "/empleado/add", method = RequestMethod.POST)
    public String setUser(@ModelAttribute Empleado empleado) {
        empleadosService.addEmpleado(empleado);
        return "redirect:/empleado/list";
    }

    @RequestMapping("/empleado/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("empleado", empleadosService.getEmpleado(id));
        return "empleado/details";
    }
    @RequestMapping("/empleado/delete/{id}")
    public String delete(@PathVariable Long id) {
        empleadosService.deleteEmpleado(id);
        return "redirect:/empleado/list";
    }
    @RequestMapping(value = "/empleado/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        Empleado empleado = empleadosService.getEmpleado(id);
        model.addAttribute("empleado", empleado);
        return "empleado/edit";
    }
    @RequestMapping(value = "/empleado/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @ModelAttribute Empleado empleado) {
        Empleado originalEmpleado = empleadosService.getEmpleado(id);
        originalEmpleado.setNombre(empleado.getNombre());
        originalEmpleado.setDni(empleado.getDni());
        originalEmpleado.setApellidos(empleado.getApellidos());

        empleadosService.addEmpleado(originalEmpleado);

        return "redirect:/empleado/details/" + id;
    }

    @RequestMapping("/empleado/list/update")
    public String updateList(Model model) {
        model.addAttribute("empleadosList", empleadosService.getEmpleados() );
        return "empleado/list :: empleadosTable";
    }

}
