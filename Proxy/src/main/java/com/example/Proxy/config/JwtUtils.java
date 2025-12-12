package com.example.Proxy.config;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtUtils {
    private final SecretKey key;

    public JwtUtils(@Value("${app.jwt.base64-secret}") String base64Secret) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}
