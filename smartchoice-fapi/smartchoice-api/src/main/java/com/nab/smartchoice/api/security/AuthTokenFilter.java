package com.nab.smartchoice.api.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nab.smartchoice.api.security.services.JwtService;
import com.nab.smartchoice.api.security.services.UserDetailsSecurityServiceImpl;

public class AuthTokenFilter extends OncePerRequestFilter {
  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserDetailsSecurityServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    final String jwt = parseJwt(httpServletRequest);
    if (jwtService.validateJwtToken(jwt)) {
      final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.getUserNameFromJwtToken(jwt));
      final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }


  private String parseJwt(HttpServletRequest request) {
    final String headerAuth = request.getHeader("Authorization");
    if (StringUtils.length(headerAuth) >= 7 && headerAuth.startsWith(JwtService.TOKEN_TYPE)) {
      return headerAuth.substring(7);
    }
    return null;
  }
}
