package com.nab.smartchoice.log.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.log.dto.LogDto;
import com.nab.smartchoice.log.services.LogService;

@RunWith(MockitoJUnitRunner.class)
public class LoggerControllerTest {

  @InjectMocks
  private LoggerController loggerController;

  @Mock
  private LogService logService;

  @Test
  public void log_shouldCallLogServiceOnce() {
    loggerController.log(LogDto.builder().build());
    Mockito.verify(logService).writeLog(Mockito.any());
  }

}