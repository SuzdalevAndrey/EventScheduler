package ru.andreyszdlv.eventscheduler.service;

import ru.andreyszdlv.eventscheduler.exception.InvalidTokenException;

public interface JwtService {

    String generateAccessToken(String email);

    String generateRefreshToken(String email);

    String extractEmail(String token);

    void validateToken(String token) throws InvalidTokenException;
}
