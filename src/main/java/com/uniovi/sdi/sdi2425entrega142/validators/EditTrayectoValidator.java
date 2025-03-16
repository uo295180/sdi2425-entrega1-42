package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EditTrayectoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Trayecto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Trayecto trayecto = (Trayecto) target;
        // Validaciones a realizar:
        // fechaFin > fechaInicio,
        // odometroFin >= odometroInicio,
        // odometroInicio && odometroFin >= 0.
        if (trayecto.getFechaFinTrayecto() != null && trayecto.getFechaInicioTrayecto() != null &&
                trayecto.getFechaFinTrayecto().isBefore(trayecto.getFechaInicioTrayecto())) {
            errors.rejectValue("fechaFinTrayecto", "Error.trayecto.edit.fechaFinAntesInicio");
        }
        if (trayecto.getOdometroFin() < trayecto.getOdometroInicio()) {
            errors.rejectValue("odometroFin", "Error.trayecto.edit.odometroFinMenorInicio");
        }
        if (trayecto.getOdometroFin() < 0) {
            errors.rejectValue("odometroFin", "Error.trayecto.edit.odometroNegativo");
        }
        if (trayecto.getOdometroInicio() < 0) {
            errors.rejectValue("odometroInicio", "Error.trayecto.edit.odometroNegativo");
        }
    }
}
