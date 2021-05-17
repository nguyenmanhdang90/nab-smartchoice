package com.nab.smartchoice.log.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nab.smartchoice.log.dto.LogDto;
import com.nab.smartchoice.log.entities.Logs;
import com.nab.smartchoice.log.repo.LogsRepository;
import com.nab.smartchoice.log.services.LogService;

@Service
public class LogServiceImpl implements LogService {

  @Autowired
  private LogsRepository logsRepository;

  @Override
  public void writeLog(LogDto log) {
    Assert.notNull(log, "Log cannot be null");
    Assert.hasText(log.getAction(), "Action cannot be empty");
    Assert.hasText(log.getUsername(), "Username cannot be empty");
    Logs entity = Logs.builder()
        .username(log.getUsername())
        .action(log.getAction())
        .details(log.getDetails())
        .timestamp(new Date())
        .build();
    logsRepository.save(entity);
  }
}
