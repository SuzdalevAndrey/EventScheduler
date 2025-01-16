package ru.andreyszdlv.eventscheduler.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.andreyszdlv.eventscheduler.exception.UserAlreadyRegisterException;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler({
            UserAlreadyRegisterException.class
    })
    public ProblemDetail handleConflictException(RuntimeException ex, Locale locale) {

        ProblemDetail response = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                messageSource.getMessage(ex.getMessage(), null, ex.getMessage(), locale)
        );

        return response;
    }

    @ExceptionHandler({
            BindException.class
    })
    public ProblemDetail handleBadRequestException(BindException ex, Locale locale) {
        ProblemDetail response = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                messageSource.getMessage("validation.error.title", null, "validation.error.title", locale)
        );
        response.setProperty(
                "errors",
                ex.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList()
        );

        return response;
    }
}
