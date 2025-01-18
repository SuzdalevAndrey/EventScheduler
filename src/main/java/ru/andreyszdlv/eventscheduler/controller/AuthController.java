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
import ru.andreyszdlv.eventscheduler.dto.auth.*;
import ru.andreyszdlv.eventscheduler.service.AuthService;
import ru.andreyszdlv.eventscheduler.validation.RequestValidator;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RequestValidator requestValidator;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(
            @RequestBody @Valid RegisterUserRequestDto registerUserDto,
            BindingResult bindingResult) throws BindException {
        log.info("Received request register user with email: {}", registerUserDto.email());

        requestValidator.validate(bindingResult);

        RegisterUserResponseDto responseDto = authService.registerUser(registerUserDto);

        log.info("User with email: {} and id: {} registered successfully", responseDto.email(), responseDto.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(
            @RequestBody @Valid LoginUserRequestDto loginUserDto,
            BindingResult bindingResult) throws BindException {
        log.info("Received request login user with email: {}", loginUserDto.email());

        requestValidator.validate(bindingResult);

        LoginUserResponseDto responseDto = authService.loginUser(loginUserDto);

        log.info("User with email: {} logged successfully", loginUserDto.email());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refreshToken(
            @RequestBody @Valid RefreshRequestDto refreshTokenDto,
            BindingResult bindingResult) throws BindException {
        log.info("Received request refresh token");

        requestValidator.validate(bindingResult);

        RefreshResponseDto responseDto = authService.refreshToken(refreshTokenDto);

        log.info("Token refreshed successfully for user");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}