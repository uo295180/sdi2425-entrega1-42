package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Vehiculo;
import com.uniovi.sdi.sdi2425entrega142.services.VehiculosService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class VehiculosValidator implements Validator {
    private final VehiculosService vehiculosService;
    public VehiculosValidator(VehiculosService vehiculosService) {
        this.vehiculosService = vehiculosService;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return Vehiculo.class.equals(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        Vehiculo vehiculo = (Vehiculo) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matricula", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroBastidor", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "marca", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "modelo", "Error.empty");

        // Validar formato de matrícula española (ejemplo: 1234BCD o O1234AB)
        String matriculaCorrecta = "^(\\d{4}[A-Z]{3}|[A-Z]\\d{4}[A-Z]{2})$";
        if (!Pattern.matches(matriculaCorrecta, vehiculo.getMatricula())) {
            errors.rejectValue("matricula", "Error.vehiculo.matricula.format");
        }
        // Verificar que la matrícula no esté repetida en el sistema
        if (vehiculosService.getVehiculoByMatricula(vehiculo.getMatricula()) != null) {
            errors.rejectValue("matricula", "Error.vehiculo.matricula.duplicate");
        }
        // Validar longitud del número de bastidor
        if (vehiculo.getNumeroBastidor().length() != 17) {
            errors.rejectValue("numeroBastidor", "Error.vehiculo.numeroBastidor.length");
        }
        // Verificar que el número de bastidor no esté repetido en el sistema
        if (vehiculosService.getVehiculoByNumeroBastidor(vehiculo.getNumeroBastidor()) != null) {
            errors.rejectValue("numeroBastidor", "Error.vehiculo.numeroBastidor.duplicate");
        }
    }
}
