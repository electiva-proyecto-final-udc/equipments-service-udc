package com.udc.gestionEquipos.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            sendJsonError(response, 401, "MISSING AUTHORIZATION HEADER", null);
            return;
        }

        String token = header.substring(7);
        try{
            if (!jwtService.isTokenValid(token)) {
                sendJsonError(response, 401, "INVALID OR EXPIRED TOKEN", null);
                return;
            }

            String username = jwtService.extractUsername(token);
            String role = jwtService.extractUserRole(token);

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            if (role != null && !role.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
        }catch (Exception e) {
            sendJsonError(response, 401, "INVALID OR EXPIRED TOKEN", e.getMessage());
        }
    }
    private void sendJsonError(HttpServletResponse response, int code, String message, String details)
            throws IOException {
        response.setStatus(code);
        response.setContentType("application/json");
        String json = String.format(
                "{ \"error\": { \"code\": %d, \"message\": \"%s\", \"details\": %s } }",
                code,
                message,
                details != null ? "\"" + details + "\"" : "null"
        );
        response.getWriter().write(json);
    }
}
