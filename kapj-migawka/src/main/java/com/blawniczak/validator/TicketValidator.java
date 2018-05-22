package com.blawniczak.validator;

import com.blawniczak.domain.TicketForm;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class TicketValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(@Nullable Object o, Errors errors) {
        TicketForm ticket = (TicketForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ticketType", "error.field.notEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "error.field.notEmpty");
        if (errors.getFieldError("startDate") == null) {
            if (ticket.getStartDate().before(new Date())) {
                errors.rejectValue("startDate", "error.date.incorrect");
            }
        }
    }
}
