package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.entities.Repostaje;
import com.uniovi.sdi.sdi2425entrega142.repository.RepostajesRepository;
import com.uniovi.sdi.sdi2425entrega142.services.RepostajesService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RepostajesValidator implements Validator {
    private final RepostajesService repostajesService;

    public RepostajesValidator(RepostajesService repostajesService) {
        this.repostajesService = repostajesService;
    }

    @Override
    public boolean supports(Class<?> clazz) { return Repostaje.class.equals(clazz); }

    @Override
    public void validate(Object target, Errors errors) {
        Repostaje repostaje = (Repostaje) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombreEstacion", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "precio", "Error.empty");
        if(!repostaje.isRepostajeCompleto())
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cantidadRepostada", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "odometro", "Error.empty");

        if (repostaje.getNombreEstacion() != null && !repostaje.getNombreEstacion().matches("^[^\\s].*[^\\s]$")) {
            errors.rejectValue("nombreEstacion", "Error.whitespace", "Nombre estación incorrecta");
        }
        if (repostaje.getPrecio() < 0) {
            errors.rejectValue("precio", "Error.repostaje.negativePrice", "Precio invalido");
        }
        if(!repostaje.isRepostajeCompleto())
            if (repostaje.getCantidadRepostada() < 0) {
                errors.rejectValue("cantidadRepostada", "Error.repostaje.negativeQuantity", "Cantidad invalida");
            }
        if (repostaje.getOdometro() <= repostaje.getVehiculo().getOdometro()) {
            errors.rejectValue("odometro", "Error.repostaje.invalidOdometer", "Odometro invalido");
        }
    }
}
