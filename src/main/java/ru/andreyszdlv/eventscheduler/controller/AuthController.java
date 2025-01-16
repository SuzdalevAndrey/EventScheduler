package ru.andreyszdlv.eventscheduler.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserResponseDto;
import ru.andreyszdlv.eventscheduler.service.AuthService;
import ru.andreyszdlv.eventscheduler.validation.RequestValidator;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RequestValidator requestValidator;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(
            @RequestBody @Valid RegisterUserRequestDto requestDto,
            BindingResult bindingResult) throws BindException {

        requestValidator.validate(bindingResult);

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(requestDto));
    }
}
