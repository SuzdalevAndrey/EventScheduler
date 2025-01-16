package ru.andreyszdlv.eventscheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.andreyszdlv.eventscheduler.dto.auth.LoginUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.LoginUserResponseDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserResponseDto;
import ru.andreyszdlv.eventscheduler.exception.UserAlreadyRegisterException;
import ru.andreyszdlv.eventscheduler.mapper.UserMapper;
import ru.andreyszdlv.eventscheduler.model.User;
import ru.andreyszdlv.eventscheduler.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {

        if(userRepository.existsByEmail(requestDto.email())) {
            throw new UserAlreadyRegisterException("error.409.user.already_exists");
        }

        User user = userMapper.toEntity(requestDto);

        user.setPassword(passwordEncoder.encode(requestDto.password()));

        return userMapper.toRegisterUserResponseDto(userRepository.save(user));
    }

    public LoginUserResponseDto loginUser(LoginUserRequestDto requestDto) {
        log.info("loginUser for email: {}", requestDto.email());
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
}
