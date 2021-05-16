package com.nab.smartchoice.crawler.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nab.smartchoice.crawler.configuration.WebSecurityConfig;

public class AuthTokenFilter extends OncePerRequestFilter {

  private static final String TOKEN = "TOKEN";

  private static final String TOKEN_TYPE = "Bearer";

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {
    final String token = parseJwt(httpServletRequest);
    if (token != null && token.equals(TOKEN)) {
      SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
          WebSecurityConfig.FAPI_USER, TOKEN, Collections.emptyList()));
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

  private String parseJwt(HttpServletRequest request) {
    final String headerAuth = request.getHeader("Authorization");
    if (headerAuth != null && headerAuth.length() >= 7 && headerAuth.startsWith(TOKEN_TYPE)) {
      return headerAuth.substring(7);
    }
    return null;
  }
}
