package ru.andreyszdlv.eventscheduler.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Component
public class RequestValidator {

    public void validate(BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
    }

}
