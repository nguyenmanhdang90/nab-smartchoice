package com.nab.smartchoice.log.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.nab.smartchoice.log.dto.LogDto;
import com.nab.smartchoice.log.repo.LogsRepository;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceImplTest {

  @InjectMocks
  private LogServiceImpl logService;

  @Mock
  private LogsRepository logsRepository;

  @Test(expected = IllegalArgumentException.class)
  public void writeLog_withNullData() {
    logService.writeLog(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeLog_withNullAction() {
    LogDto logDto = LogDto.builder().build();
    logService.writeLog(logDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeLog_withNullUser() {
    LogDto logDto = LogDto.builder().action("action").build();
    logService.writeLog(logDto);
  }

  @Test(expected = IllegalArgumentException.class)
  public void writeLog_withEmptyAction() {
    LogDto logDto = LogDto.builder().action("").build();
    logService.writeLog(logDto);
  }

  @Test
  public void writeLog() {
    LogDto logDto = LogDto.builder().action("action").username("username").build();
    logService.writeLog(logDto);
  }


}