package com.nab.smartchoice.log.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nab.smartchoice.log.dto.LogDto;
import com.nab.smartchoice.log.services.LogService;

@RestController
@RequestMapping("/api/log")
public class LoggerController {

  @Autowired
  private LogService logService;

  @PostMapping
  public ResponseEntity<Void> log(@RequestBody LogDto logDto) {
    logService.writeLog(logDto);
    return ResponseEntity.ok().build();
  }
}
