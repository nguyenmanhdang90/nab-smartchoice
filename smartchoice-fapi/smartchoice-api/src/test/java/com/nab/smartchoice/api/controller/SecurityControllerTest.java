package com.nab.smartchoice.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.nab.smartchoice.api.security.model.LoginRequest;
import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;
import com.nab.smartchoice.api.security.services.JwtService;

@RunWith(MockitoJUnitRunner.class)
public class SecurityControllerTest {

  @InjectMocks
  private SecurityController securityController;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @Test
  public void authenticateUser() {
    Mockito.when(authenticationManager.authenticate(Mockito.any()))
        .thenReturn(new UsernamePasswordAuthenticationToken(UserDetailsPrinciple.builder().build(), ""));
    securityController.authenticateUser(LoginRequest.builder().build());
    Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(Mockito.any());
    Mockito.verify(jwtService, Mockito.times(1)).generateJwtToken(Mockito.any());
  }

}