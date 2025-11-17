package com.udc.gestionEquipos.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public String extractUsername(String token) {
        try {
            Claims claims = extractAllClaims(token);

            // ✅ Leer el objeto "UserData"
            Map<String, Object> userData = (Map<String, Object>) claims.get("UserData");

            if (userData != null && userData.get("Username") != null) {
                return (String) userData.get("Username");
            }

            // fallback al sub clásico
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String extractUserRole(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Map<String, Object> userData = (Map<String, Object>) claims.get("UserData");

            if (userData != null && userData.get("Role") != null) {
                return (String) userData.get("Role");
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }
}
