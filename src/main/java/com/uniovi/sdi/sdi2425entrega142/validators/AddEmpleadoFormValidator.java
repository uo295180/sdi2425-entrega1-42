package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddEmpleadoFormValidator implements Validator {
    private final EmpleadosService empleadosService;

    public AddEmpleadoFormValidator(EmpleadosService empleadosService) {
        this.empleadosService = empleadosService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Empleado.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Empleado empleado = (Empleado) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.add.empleado.dni.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "Error.add.empleado.nombre.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellidos", "Error.add.empleado.apellidos.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "Error.add.empleado.role.empty");
        if (!empleado.getDni().matches("[0-9]{8}[A-Z]")) {
            errors.rejectValue("dni", "Error.add.empleado.dni.structure");}
        if (empleadosService.existsEmpleado(empleado.getDni())) {
            errors.rejectValue("dni", "Error.add.empleado.dni.uniqueness");}
    }
}