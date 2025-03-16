package com.uniovi.sdi.sdi2425entrega142.validators;

import com.uniovi.sdi.sdi2425entrega142.dtos.PasswordDTO;
import com.uniovi.sdi.sdi2425entrega142.entities.Empleado;
import com.uniovi.sdi.sdi2425entrega142.services.EmpleadosService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ChangePasswordFormValidator implements Validator {
    private final EmpleadosService empleadosService;

    public ChangePasswordFormValidator(EmpleadosService empleadosService) {
        this.empleadosService = empleadosService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Empleado.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordDTO dto = (PasswordDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actualPassword", "Error.edit.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "Error.edit.password.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword", "Error.edit.password.empty");
        if (!empleadosService.matchesPassword(dto.getActualPassword(), dto.getId())) {
            errors.rejectValue("actualPassword", "Error.edit.password.actualPassword.notMatch");}
        if (!isValidPassword(dto.getNewPassword())) {
            errors.rejectValue("newPassword", "Error.edit.password.newPassword.invalid");}
        if (!dto.getNewPassword().equals(dto.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "Error.edit.password.repeatPassword.notMatch");}

    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 12) {
            return false;
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-+]).{12,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }
}