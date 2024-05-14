package com.inkronsane.Library.service;


import com.inkronsane.Library.entity.*;
import io.jsonwebtoken.*;
import java.nio.charset.*;
import java.util.*;
import java.util.function.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.springframework.stereotype.*;

@Service
public class JwtService {

   private static final long jwtLifetime = 8640000;
   private final SecretKey key;


   public JwtService() {
      String secretString = "BSVBSHBVSBVSHBDXXXYTDT76587575785HJFVTYFFGHVGCFMUD";
      byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
      this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
   }


   public String generateToken(User userDetails) {
      return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtLifetime))
        .signWith(key)
        .compact();
   }

   public String generateRefreshToken(HashMap<String, Object> claims, User userDetails) {
      return Jwts.builder()
        .claims(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtLifetime))
        .signWith(key)
        .compact();
   }

   public String extractUsername(String token) {
      return extractClaims(token, Claims::getSubject);
   }

   private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
      return claimsTFunction.apply(
        Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload());
   }

   public boolean isTokenValid(String token, User userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
   }

   private boolean isTokenExpired(String token) {
      return extractClaims(token, Claims::getExpiration).before(new Date());
   }
}