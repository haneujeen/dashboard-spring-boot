/**
 * This class provides a service for generating and validating JWT tokens for user authentication.
 * It contains a method for generating a token based on a UserEntity object and another method for validating and retrieving
 * the user ID from a token string.
 * The generate method builds a JWT token with a payload containing the user ID, the token issuer, and expiration date.
 * The validateAndGetUserId method parses a given token string and retrieves the user ID from its payload.
 * The SECRET_KEY variable is used as the secret key for generating and validating tokens.
 */
package com.example.shop.security;

import com.example.shop.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "mysecretkey";

    /**
     * Generates a JWT token for the given user.
     *
     * @param userEntity the user entity for which to generate the token
     * @return the generated token
     */
    public static String generate(UserEntity userEntity) {
        Date expiryDate = Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS));

        /*
        Header: {"alg": "HS512"}
        Payload: {"sub":"user_id",
                    "iss": "mycompany",
                    "ist": "token_type",
                    "exp": "2023-05-09T10:00:00Z"}
        Secret Key: "mysecretkey"
        */
        return Jwts.builder()
                .setIssuer("shop")
                .setSubject(userEntity.getId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * Validates the given JWT token and returns the user ID contained within it.
     *
     * @param token the token to validate
     * @return the user ID contained within the token
     */
    public static String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
