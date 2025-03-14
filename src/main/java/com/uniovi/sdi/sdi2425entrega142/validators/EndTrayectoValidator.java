package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class EndTrayectoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Trayecto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Trayecto trayecto = (Trayecto) target;

        if(trayecto.getOdometroFin() < 0){
            errors.rejectValue("odometroFin", "Error.trayecto.end.negativeOdometro");
        }
        else if(trayecto.getVehiculo().getOdometro() >= trayecto.getOdometroFin()) {
            errors.rejectValue("odometroFin", "Error.trayecto.end.odometro");
        }
    }

}
