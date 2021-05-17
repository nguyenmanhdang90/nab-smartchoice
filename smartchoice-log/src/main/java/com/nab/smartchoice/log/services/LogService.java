package com.nab.smartchoice.log.services;

import com.nab.smartchoice.log.dto.LogDto;

public interface LogService {

  void writeLog(LogDto log);
}
