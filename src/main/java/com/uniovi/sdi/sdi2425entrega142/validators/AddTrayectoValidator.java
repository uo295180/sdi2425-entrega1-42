package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Trayecto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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

        // Validación de empleado
        if (trayecto.getEmpleado() == null) {
            errors.rejectValue("empleado", "Error.trayecto.add.empleado.required");
        } else {
            Set<Trayecto> trayectos = trayecto.getEmpleado().getTrayectos();
            if (trayectos != null && !trayectos.isEmpty()) {
                for (Trayecto t : trayectos) {
                    if (t.isEstadoTrayecto()) {
                        errors.rejectValue("estadoTrayecto", "Error.trayecto.add.estado");
                    }
                }
            }
        }

        // Validación de vehículo
        if (trayecto.getVehiculo() == null) {
            errors.rejectValue("vehiculo", "Error.trayecto.add.vehiculo.required");
        } else {
            // Verificación de vehículo activo según el estado
            if (trayecto.getVehiculo().isEstadoVehiculo()) {
                errors.rejectValue("vehiculo", "Error.trayecto.add.vehiculo");
            }
        }
    }
}
