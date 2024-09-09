package com.example.racunapp2.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Collections;
import java.util.Date;

public final class JwtUtil {
    private JwtUtil() {
    }

    private static final String secretKey = "YXBpX2tleSA9IHJjYS5waWJzYW9yY2lidTIzNGxidTQz"; // "secure" key

    // Generate a JWT token
    public static String generateToken(Integer id, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate a JWT token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get username from JWT token
    public static LoggedInUser extractUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        Integer id = claims.get("id", Integer.class);
        return new LoggedInUser(id, claims.getSubject(), null, Collections.emptyList());
    }
}
