package com.digis01.FCruzProgramacionNCapasWebSpring.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final String SECRET_KEY = "clave_super_secreta_muy_larga_123456789";

    public String generateToken(UserDetails userDetails) {

        CustomUserDetails user = (CustomUserDetails) userDetails;

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("nombre", user.getNombreCompleto())
                .claim("rol", user.getRol())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
    
    public String extractClaim(String token, String claim) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(claim, String.class);
    }
    
    

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
