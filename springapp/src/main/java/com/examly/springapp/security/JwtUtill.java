package com.examly.springapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtill {

    @Value("${jwt.jwtsecret}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration}")
    private long expiration;

    private Key key()
    {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    

    public String generateAccessToken(UserPrinciple userPrinciple){

        String roles = userPrinciple.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.joining(","));

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        
        return Jwts.builder()
            .setSubject(userPrinciple.getEmail())
            .claim("id" ,userPrinciple.getId())
            .claim("name" , userPrinciple.getName())
            .claim("role" , roles)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key(), SignatureAlgorithm.HS512)
            .compact();
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }   

    public String extractEmail(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }   


    // Inside JwtUtils.java
    public Boolean validateToken(String token) {
        try {
            final String email = extractEmail(token);
            return (email != null && !isTokenExpired(token));
        } catch (Exception e) {
            // Catches SignatureException, ExpiredJwtException, MalformedJwtException, etc.
            System.out.println("JWT Validation failed: " + e.getMessage());
            return false; // Safely returns false instead of crashing the server!
        }
}


}
