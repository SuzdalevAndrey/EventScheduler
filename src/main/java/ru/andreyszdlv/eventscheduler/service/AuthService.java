package ru.andreyszdlv.eventscheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.andreyszdlv.eventscheduler.dto.auth.*;
import ru.andreyszdlv.eventscheduler.exception.InvalidRefreshTokenException;
import ru.andreyszdlv.eventscheduler.exception.InvalidTokenException;
import ru.andreyszdlv.eventscheduler.exception.UserAlreadyRegisterException;
import ru.andreyszdlv.eventscheduler.mapper.UserMapper;
import ru.andreyszdlv.eventscheduler.model.User;
import ru.andreyszdlv.eventscheduler.repository.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    @Transactional
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserDto) {
        String userEmail = registerUserDto.email();
        log.info("Registering new user with email: {}", userEmail);

        log.info("Checking user exists with email {}", userEmail);
        if(userRepository.existsByEmail(userEmail)) {
            log.error("User with email {} already exists", userEmail);
            throw new UserAlreadyRegisterException("error.409.user.already_exists");
        }

        User user = userMapper.toEntity(registerUserDto);

        user.setPassword(passwordEncoder.encode(registerUserDto.password()));

        log.info("User with email: {} registered successfully", userEmail);
        return userMapper.toRegisterUserResponseDto(userRepository.save(user));
    }

    @Transactional
    public LoginUserResponseDto loginUser(LoginUserRequestDto requestDto) {
        log.info("Logging user with email: {}", requestDto.email());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(),
                        requestDto.password()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        log.info("User login successfully with email: {}", requestDto.email());
        return new LoginUserResponseDto(accessToken, refreshToken);
    }

    public RefreshResponseDto refreshToken(RefreshRequestDto refreshTokenDto) {
        log.info("Refreshing token");

        String refreshToken = refreshTokenDto.refreshToken();

        try {
            jwtService.validateToken(refreshToken);
        } catch (InvalidTokenException e) {
            log.error("This refresh token invalid");
            throw new InvalidRefreshTokenException("error.401.refresh-token.invalid");
        }

        String email = jwtService.extractEmail(refreshToken);

        log.info("Token refresh successfully for user: {}", email);
        return RefreshResponseDto.builder()
                .accessToken(jwtService.generateAccessToken(email))
                .refreshToken(jwtService.generateRefreshToken(email))
                .build();
    }
}
