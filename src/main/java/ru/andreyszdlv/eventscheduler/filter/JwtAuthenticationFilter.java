package ru.andreyszdlv.eventscheduler.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.andreyszdlv.eventscheduler.exception.InvalidTokenException;
import ru.andreyszdlv.eventscheduler.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MessageSource messageSource;

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractToken(request);

        try {
            if (token != null) {
                jwtService.validateToken(token);

                String username = jwtService.extractEmail(token);
                log.info("Extracted username: {}", username);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null);
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("User authenticated: {}", username);
            }

            filterChain.doFilter(request, response);
        } catch (InvalidTokenException ex) {
            log.error("Invalid token, send response unauthorized for user");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    messageSource.getMessage("error.403.user.unauthorized", null, "error.401.user.unauthorized", request.getLocale())
            );
        }
    }

    private String extractToken(HttpServletRequest request) {
        log.info("Extract token from request header");
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            log.info("token != null");
            return bearerToken.substring(7);
        }
        log.info("token == null");
        return null;
    }
}