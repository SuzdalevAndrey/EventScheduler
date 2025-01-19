package ru.andreyszdlv.eventscheduler.service;

import ru.andreyszdlv.eventscheduler.enums.Role;
import ru.andreyszdlv.eventscheduler.exception.InvalidTokenException;

public interface JwtService {

    String generateAccessToken(String email, Role role);

    String generateRefreshToken(String email, Role role);

    String extractRole(String token);

    String extractEmail(String token);

    void validateToken(String token) throws InvalidTokenException;
}
