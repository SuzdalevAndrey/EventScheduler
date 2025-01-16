package ru.andreyszdlv.eventscheduler.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.andreyszdlv.eventscheduler.dto.auth.LoginUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.LoginUserResponseDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserResponseDto;
import ru.andreyszdlv.eventscheduler.service.AuthService;
import ru.andreyszdlv.eventscheduler.validation.RequestValidator;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    private final RequestValidator requestValidator;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(
            @RequestBody @Valid RegisterUserRequestDto requestDto,
            BindingResult bindingResult) throws BindException {

        log.info("Registering user: {}", requestDto);
        requestValidator.validate(bindingResult);

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registerUser(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(
            @RequestBody @Valid LoginUserRequestDto requestDto,
            BindingResult bindingResult) throws BindException {

       requestValidator.validate(bindingResult);

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.loginUser(requestDto));
    }
}