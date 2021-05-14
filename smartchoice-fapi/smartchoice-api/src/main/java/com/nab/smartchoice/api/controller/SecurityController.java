package com.nab.smartchoice.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.api.security.model.JwtResponse;
import com.nab.smartchoice.api.security.model.LoginRequest;
import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;
import com.nab.smartchoice.api.security.services.JwtService;

@RestController
@RequestMapping("/auth")
public class SecurityController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/signIn")
  public ResponseEntity<JwtResponse<UserDetails>> authenticateUser(@RequestBody LoginRequest loginRequest) {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final UserDetailsPrinciple userDetails = (UserDetailsPrinciple) authentication.getPrincipal();
    return ResponseEntity.ok(JwtResponse.builder()
        .token(jwtService.generateJwtToken(authentication))
        .type(JwtService.TOKEN_TYPE)
        .userDetails(userDetails)
        .build());
  }
}
