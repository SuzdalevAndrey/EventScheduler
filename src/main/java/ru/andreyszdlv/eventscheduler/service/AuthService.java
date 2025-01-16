package ru.andreyszdlv.eventscheduler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserRequestDto;
import ru.andreyszdlv.eventscheduler.dto.auth.RegisterUserResponseDto;
import ru.andreyszdlv.eventscheduler.exception.UserAlreadyRegisterException;
import ru.andreyszdlv.eventscheduler.mapper.UserMapper;
import ru.andreyszdlv.eventscheduler.model.User;
import ru.andreyszdlv.eventscheduler.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {

        if(userRepository.existsByEmail(requestDto.email())) {
            throw new UserAlreadyRegisterException("error.409.user.already_exists");
        }

        User user = userMapper.toEntity(requestDto);

        user.setPassword(passwordEncoder.encode(requestDto.password()));

        return userMapper.toRegisterUserResponseDto(userRepository.save(user));
    }
}
