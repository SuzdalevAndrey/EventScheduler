package ru.andreyszdlv.eventscheduler.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

@Component
@Slf4j
public class RequestValidator {

    public void validate(BindingResult bindingResult) throws BindException {

        if(bindingResult.hasErrors()) {
            log.error("Validation failed: {}", bindingResult.getAllErrors());
            throw new BindException(bindingResult);
        }

        log.info("Validation successful");
    }

}
