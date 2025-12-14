package com.mycompany.myapp.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jhipster.security.authentication.jwt.base64-secret}")
    private String base64Secret;

    @Value("${jhipster.security.authentication.jwt.token-validity-in-seconds}")
    private long jwtValiditySeconds;

    private Key key;

    @PostConstruct
    public void init() {
        if (base64Secret == null || base64Secret.trim().isEmpty()) {
            log.error("ERROR: La propiedad 'jhipster.security.authentication.jwt.base64-secret' no está configurada");
            throw new IllegalStateException(
                "La propiedad 'jhipster.security.authentication.jwt.base64-secret' debe estar configurada en application.yml"
            );
        }

        try {
            log.info("Inicializando JwtUtils con secreto Base64");
            key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
            log.info("JwtUtils inicializado correctamente");
        } catch (IllegalArgumentException e) {
            log.error("ERROR: El secreto Base64 no es válido: {}", e.getMessage());
            throw new IllegalStateException("El secreto Base64 proporcionado no es válido", e);
        }
    }

    public String generateServiceToken() {
        Instant now = Instant.now();

        return Jwts.builder()
            .setSubject("backend-service")
            .claim("role", "ROLE_SERVICE")
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plusSeconds(jwtValiditySeconds)))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    /**
     * Obtiene la clave secreta para validar el token
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = base64Secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Valida el token JWT
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Token inválido: {}", e.getMessage());
            return false;
        }
    }
    /**
     * Extrae todos los claims del token
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            log.error("Error extrayendo claims: {}", e.getMessage());
            return null;
        }
    }
    /**
     * Extrae el subject (username) del token
     */
    public String getSubjectFromToken(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }
    /**
     * Verifica si el token ha expirado
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaims(token);
            if (claims == null) return true;

            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}
