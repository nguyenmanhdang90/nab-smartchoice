package com.nab.smartchoice.api.security.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtService {
  public static final String TOKEN_TYPE = "Bearer";

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationMs}")
  private int jwtExpirationMs;

  public String generateJwtToken(Authentication authentication) {
    final UserDetailsPrinciple userPrincipal = (UserDetailsPrinciple) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.debug("SignatureException JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.debug("MalformedJwtException JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.debug("ExpiredJwtException token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.debug("UnsupportedJwtException token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.debug("IllegalArgumentException claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
