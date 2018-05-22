package com.blawniczak.validator;

import com.blawniczak.domain.EditUser;
import com.blawniczak.domain.User;
import com.blawniczak.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService userService;

    public boolean supports(Class<?> aClass) {
        return false;
    }

    public void validate(@Nullable Object o, Errors errors) {

        User user = (User) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.length");
        if (errors.getFieldError("username") == null
                && user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "error.username.length");
        } else if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "error.username.duplicate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.length");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "error.password.length");
        if (errors.getFieldError("password") == null) {
            if (user.getPassword().length() < 8 || user.getPassword().length() > 64) {
                errors.rejectValue("password", "error.password.length");
            } else if (!user.getPassword().matches(".*\\d+.*")
                    || user.getPassword().toLowerCase().equals(user.getPassword())) {
                errors.rejectValue("password", "error.password.content");
            } else if (!user.getPassword().equals(user.getPasswordConfirm())) {
                errors.rejectValue("passwordConfirm", "error.password.mismatch");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.field.notEmpty");
        if (errors.getFieldError("name") == null) {
            if (!user.getName().matches("([A-Z][a-z]+)")) {
                errors.rejectValue("name", "error.field.incorrectString");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "error.field.notEmpty");
        if (errors.getFieldError("surname") == null) {
            if (!user.getSurname().matches("([A-Z][a-z]+)")) {
                errors.rejectValue("surname", "error.field.incorrectString");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.field.notEmpty");
        if (errors.getFieldError("email") == null) {
            if (!user.getEmail().matches("([a-zA-Z0-9]+)(.?)([a-zA-Z0-9]+)@([a-z0-9]+).([a-z0-9]+)")) {
                errors.rejectValue("email", "error.email.format");
            } else if (userService.findByEmail(user.getEmail()) != null) {
                errors.rejectValue("email", "error.email.duplicate");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "error.field.notEmpty");
        if (errors.getFieldError("birthDate") == null) {
            if (user.getBirthDate().after(new Date())) {
                errors.rejectValue("birthDate", "error.date.incorrect");
            }
        }
    }

    public void validateUpdate(@Nullable Object o, Errors errors) {
        EditUser user = (EditUser) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.length");
        if (errors.getFieldError("username") == null
                && user.getUsername().length() < 6 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "error.username.length");
        } else if (userService.findByUsername(user.getUsername()) != null) {
            User foundUser = userService.findByUsername(user.getUsername());
            if(!foundUser.getId().equals(user.getId()))
                errors.rejectValue("username", "error.username.duplicate");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.field.notEmpty");
        if (errors.getFieldError("name") == null) {
            if (!user.getName().matches("([A-Z][a-z]+)")) {
                errors.rejectValue("name", "error.field.incorrectString");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "error.field.notEmpty");
        if (errors.getFieldError("surname") == null) {
            if (!user.getSurname().matches("([A-Z][a-z]+)")) {
                errors.rejectValue("surname", "error.field.incorrectString");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.field.notEmpty");
        if (errors.getFieldError("email") == null) {
            if (!user.getEmail().matches("([a-zA-Z0-9]+)(.?)([a-zA-Z0-9]+)@([a-z0-9]+).([a-z0-9]+)")) {
                errors.rejectValue("email", "error.email.format");
            } else if (userService.findByEmail(user.getEmail()) != null) {
                User foundUser = userService.findByEmail(user.getEmail());
                if(!foundUser.getId().equals(user.getId()))
                    errors.rejectValue("email", "error.email.duplicate");            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "birthDate", "error.field.notEmpty");
        if (errors.getFieldError("birthDate") == null) {
            if (user.getBirthDate().after(new Date())) {
                errors.rejectValue("birthDate", "error.date.incorrect");
            }
        }
    }
}
