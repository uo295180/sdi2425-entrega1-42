package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class AddTrayectoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Trayecto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Trayecto trayecto = (Trayecto) target;
        // Validaciones extra
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "score", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");

        Set<Trayecto> trayectos = trayecto.getEmpleado().getTrayectos();
        for (Trayecto t : trayectos) {
            if (t.isEstadoTrayecto()) { // TODO: QUÉ ES TRUE Y QUÉ ES FALSE ???
                errors.rejectValue("estadoTrayecto", "Error.trayecto.add.estado");
            }
        }

        if (!trayecto.getVehiculo().isEstadoVehiculo()) {
            errors.rejectValue("vehiculo", "Error.trayecto.add.vehiculo");
        }
    }
}
