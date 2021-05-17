package com.nab.smartchoice.api.services.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;
import com.nab.smartchoice.api.services.ExternalLogService;

@Service
public class ExternalLogServiceImpl implements ExternalLogService {

  @Value("${logs.url}")
  private String url;

  @Autowired
  @Qualifier("restTemplateForLogs")
  private RestTemplate restTemplate;

  @Override
  @Async
  public void sentLog(String action, String details) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetailsPrinciple user = (UserDetailsPrinciple) authentication.getPrincipal();
    HashMap<String, String> requestBody = new HashMap<>();
    requestBody.put("username", user.getUsername());
    requestBody.put("action", action);
    requestBody.put("details", details);
    restTemplate.postForEntity(url, requestBody, Void.class);
  }
}
