package com.nab.smartchoice.api.services.impl;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.nab.smartchoice.api.dto.LogAction;
import com.nab.smartchoice.api.security.model.UserDetailsPrinciple;

@RunWith(MockitoJUnitRunner.class)
public class ExternalLogServiceImplTest {

  @InjectMocks
  private ExternalLogServiceImpl externalLogService;

  @Mock
  private RestTemplate restTemplate;

  @Test
  public void sentLog() {
    String details = "details";
    ReflectionTestUtils.setField(externalLogService, "url", "url");
    Authentication authentication = Mockito.mock(Authentication.class);
    UserDetailsPrinciple userDetailsPrinciple = Mockito.mock(UserDetailsPrinciple.class);
    Mockito.when(authentication.getPrincipal()).thenReturn(userDetailsPrinciple);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    
    externalLogService.sentLog(LogAction.GET_PRICE.name(), details);

    Mockito.verify(restTemplate).postForEntity(Mockito.anyString(), Mockito.any(HashMap.class), Mockito.any());
  }

}