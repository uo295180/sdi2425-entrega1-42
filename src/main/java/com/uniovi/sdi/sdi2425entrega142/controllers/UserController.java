package com.uniovi.sdi.sdi2425entrega142.controllers;

import com.uniovi.sdi.sdi2425entrega142.dtos.PasswordDTO;
import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import com.uniovi.sdi.sdi2425entrega142.services.LoggingService;
import com.uniovi.sdi.sdi2425entrega142.services.RolesService;
import com.uniovi.sdi.sdi2425entrega142.validators.AddEmpleadoFormValidator;
import com.uniovi.sdi.sdi2425entrega142.validators.ChangePasswordFormValidator;
import com.uniovi.sdi.sdi2425entrega142.validators.EditEmpleadoFormValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    private final EmpleadosService empleadosService;
    private final RolesService rolesService;
    private final AddEmpleadoFormValidator addEmpleadoFormValidator;
    private final EditEmpleadoFormValidator editEmpleadoFormValidator;
    private final ChangePasswordFormValidator changePasswordFormValidator;
    private final LoggingService loggingService;

    public UserController(EmpleadosService empleadosService, RolesService rolesService,
                          AddEmpleadoFormValidator addEmpleadoFormValidator,
                          EditEmpleadoFormValidator editEmpleadoFormValidator, ChangePasswordFormValidator changePasswordFormValidator, LoggingService loggingService) {
        this.empleadosService = empleadosService;
        this.rolesService = rolesService;
        this.addEmpleadoFormValidator = addEmpleadoFormValidator;
        this.editEmpleadoFormValidator = editEmpleadoFormValidator;
        this.changePasswordFormValidator = changePasswordFormValidator;
        this.loggingService = loggingService;
    }



    @RequestMapping("/empleado/list")
    public String getListado(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Empleado> empleados = empleadosService.getEmpleados(pageable);

        int totalPages = empleados.getTotalPages();
        if (totalPages < 3) {
            totalPages = 3;
        }

        model.addAttribute("page", empleados);
        model.addAttribute("empleadosList", empleados.getContent());
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "empleado/list";
    }

    @RequestMapping(value = "/empleado/add")
    public String getUser(Model model) {

        Empleado empleado = new Empleado();
        empleado.setPassword(empleadosService.generatePassword());
        model.addAttribute("registrado", false);
        model.addAttribute("empleado", empleado);
        return "empleado/add";
    }

    @RequestMapping(value = "/empleado/add", method = RequestMethod.POST)
    public String setUser(Model model, @Validated Empleado empleado, BindingResult result, HttpSession session) {
        empleado.setRole(rolesService.getRoles()[0]);
        addEmpleadoFormValidator.validate(empleado, result);

        if (result.hasErrors()) {
            return "empleado/add";
        }
        empleadosService.addEmpleado(empleado);
        model.addAttribute("registrado", true);
        loggingService.logUserCreation(empleado.getDni());

        return "redirect:/empleado/add";
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
        model.addAttribute("rolesList", rolesService.getRoles());
        model.addAttribute("empleado", empleado);
        return "empleado/edit";
    }

    @RequestMapping(value = "/empleado/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@PathVariable Long id, @Validated Empleado empleado, BindingResult result, Model model) {
        editEmpleadoFormValidator.validate(empleado, result);

        if (result.hasErrors()) {
            model.addAttribute("rolesList", rolesService.getRoles());
            return "/empleado/edit";
        }

        Empleado originalEmpleado = empleadosService.getEmpleado(id);
        originalEmpleado.setNombre(empleado.getNombre());
        originalEmpleado.setDni(empleado.getDni());
        originalEmpleado.setApellidos(empleado.getApellidos());

        if (!originalEmpleado.getRole().equals(empleado.getRole())) {
            originalEmpleado.setRole(empleado.getRole());
            empleadosService.addEmpleado(originalEmpleado);

            //ACTUALIZAMOS LA SESIÓN CON LOS NUEVOS PERMISOS
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication.getName().equals(originalEmpleado.getDni())) { // Verifica si es el usuario actual
                List<GrantedAuthority> updatedAuthorities = List.of(new SimpleGrantedAuthority("ROLE_" + empleado.getRole()));
                Authentication newAuth = new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal(),
                        authentication.getCredentials(),
                        updatedAuthorities
                );
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
            return "redirect:/empleado/details/" + id;
        }
        empleadosService.addEmpleado(originalEmpleado);
        return "redirect:/empleado/details/" + id;
    }

    @RequestMapping("/empleado/list/update")
    public String updateList(Model model, Pageable pageable) {
        Page<Empleado> empleados = empleadosService.getEmpleados(pageable);
        model.addAttribute("empleadosList", empleados );
        return "empleado/list :: empleadosTable";
    }

    @RequestMapping("/empleado/password")
    public String changePassword(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String dni;

        if (principal instanceof UserDetails) {
            dni = ((UserDetails) principal).getUsername();
        } else {
            dni = principal.toString();
        }

        Empleado empleado = empleadosService.getByDni(dni);
        PasswordDTO dto = new PasswordDTO();
        dto.setId(empleado.getId());
        model.addAttribute("passwordDto", dto);

        return "/empleado/password";
    }

    @RequestMapping(value = "/empleado/password", method = RequestMethod.POST)
    public String changePassword(@ModelAttribute("passwordDto") @Validated PasswordDTO passwordDto, BindingResult result, Model model) {
        changePasswordFormValidator.validate(passwordDto, result);
        if (result.hasErrors()) {
            return "/empleado/password";
        }
        empleadosService.changePassword(passwordDto);
        return "redirect:/home";
    }
}
