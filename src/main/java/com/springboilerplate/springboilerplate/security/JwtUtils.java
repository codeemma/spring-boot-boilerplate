package com.springboilerplate.springboilerplate.security;

import com.springboilerplate.springboilerplate.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WFG on 6/22/2017.
 */
@Component
public class JwtUtils {
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expiration}")
    private Long expiration;
    @Autowired
    private UserRepository userRepository;

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.get("username", String.class);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

//    public String getDeviceFromToken(String token) {
//        String device;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            device = claims.get("device", String.class);
//        } catch (Exception e) {
//            device = null;
//        }
//        return device;
//    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        return generateClaims(claims);
    }

    private String generateClaims(Map<String, Object> claims) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setIssuedAt(generateCurrentDate());
        jwtBuilder.setExpiration(generateExpirationDate());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secret);
        return jwtBuilder.compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && (!isTokenExpired(token))

        );
    }

}