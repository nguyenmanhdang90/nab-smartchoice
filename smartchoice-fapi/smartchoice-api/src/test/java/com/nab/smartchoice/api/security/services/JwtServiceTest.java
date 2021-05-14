package com.nab.smartchoice.api.security.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class JwtServiceTest {

  @InjectMocks
  private JwtService jwtService;

  @Before
  public void setUp() {
    ReflectionTestUtils.setField(jwtService, "jwtSecret", "jwtSecret");
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 900000);
  }

  @Test
  public void generateJwtToken_success() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    String token = jwtService.generateJwtToken(authentication);
    assertTrue(jwtService.validateJwtToken(token));
    Assert.assertEquals(jwtService.getUserNameFromJwtToken(token), userDetailsPrinciple.getUsername());
  }

  @Test
  public void generateJwtToken_malformedJwtException() {
    assertFalse(jwtService.validateJwtToken("random token"));
  }

  @Test
  public void generateJwtToken_illegalArgumentException() {
    assertFalse(jwtService.validateJwtToken(null));
  }

  @Test
  public void generateJwtToken_expiredJwtException() {
    UserDetailsPrinciple userDetailsPrinciple = new UserDetailsPrinciple();
    Authentication authentication = Mockito.mock(Authentication.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 0);
    String token = jwtService.generateJwtToken(authentication);
    assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  public void generateJwtToken_signatureException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .signWith(SignatureAlgorithm.HS512, "jwtSecret111")
        .compact();
    assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  public void generateJwtToken_unsupportedJwtException() {
    String token = Jwts.builder()
        .setSubject("dang")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + 900000))
        .compact();
    assertFalse(jwtService.validateJwtToken(token));
  }

  @Test
  public void generatedToken() {
    String token = JwtService.TOKEN_TYPE + " " + Jwts.builder()
        .setSubject("nab")
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + Integer.MAX_VALUE))
        .signWith(SignatureAlgorithm.HS512, "ourSecurityKey")
        .compact();
    log.info(token);
    Assert.assertNotNull(token);
  }
}
