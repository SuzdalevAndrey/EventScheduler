package ru.andreyszdlv.eventscheduler.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.andreyszdlv.eventscheduler.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class DefaultJwtService implements JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.life-time.access}")
    private int lifeTimeAccessTokenInMinutes;

    @Value("${jwt.life-time.refresh}")
    private int lifeTimeRefreshTokenInMinutes;

    private SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    @Override
    public String generateAccessToken(String email) {
        log.info("Executing generateAccessToken in JwtSecurityService");
        return generateToken(email, lifeTimeAccessTokenInMinutes);
    }

    @Override
    public String generateRefreshToken(String email) {
        log.info("Executing generateRefreshToken in JwtSecurityService");
        return generateToken(email, lifeTimeRefreshTokenInMinutes);
    }

    @Override
    public String extractEmail(String token) {
        log.info("Extract user email from token");
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public void validateToken(String token) throws InvalidTokenException {
        log.info("Validate token in JwtSecurityService");
        if(!extractExpiration(token).before(new Date()))
            throw new InvalidTokenException();
    }

    private String generateToken(String email, long lifeTime) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + lifeTime))
                .signWith(getSigningKey())
                .compact();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        log.info("Extract all claims from token");
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
