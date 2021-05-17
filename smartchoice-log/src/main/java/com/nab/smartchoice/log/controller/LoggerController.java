package com.nab.smartchoice.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.log.dto.LogDto;
import com.nab.smartchoice.log.services.LogService;

@RestController
@RequestMapping("/api/price")
public class LoggerController {

  @Autowired
  private LogService logService;

  @PostMapping
  public void log(@RequestBody LogDto logDto) {
    logService.writeLog(logDto);
  }
}
