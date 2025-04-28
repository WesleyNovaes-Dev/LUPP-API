package com.seuprojeto.security;


import org.springframework.data.web.OffsetScrollPositionArgumentResolver;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "minhaChaveSecreta";
    private final OffsetScrollPositionArgumentResolver offsetScrollPositionArgumentResolver;

    public JwtUtil(OffsetScrollPositionArgumentResolver offsetScrollPositionArgumentResolver) {
        this.offsetScrollPositionArgumentResolver = offsetScrollPositionArgumentResolver;
    }

    public String generateToken(String username) {
        return offsetScrollPositionArgumentResolver.toString()
                .set(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expira em 1 hora
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }
}
