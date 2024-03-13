package com.uliana.MedicalSystemApi.security;

import com.uliana.MedicalSystemApi.exception.AuthenticationException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String HEADER = "Authorization";
    private static final String BEARER_TOKEN_START = "Bearer ";
    private static final int HEAD = 7;

    private final UserDetailsService userDetailsService;

    @Value("${security.jwt.token.secret-key}")
    private String jwtSigningKey;
    @Value("${security.jwt.token.expire-length}")
    private long validityInHours;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =  extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSigningKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String createToken(String login) {
        Claims claims = Jwts.claims().setSubject(login);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(validityInHours)))
                .signWith(SignatureAlgorithm.HS256, jwtSigningKey).compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationException("Invalid token", e);
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER);
        return bearerToken != null && bearerToken.startsWith(BEARER_TOKEN_START)
                ? bearerToken.substring(HEAD)
                : null;
    }
}
