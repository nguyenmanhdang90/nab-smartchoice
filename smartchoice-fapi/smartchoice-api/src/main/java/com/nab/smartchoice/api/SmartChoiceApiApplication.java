package com.nab.smartchoice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import com.nab.smartchoice.db.SmartChoiceDBApplication;

import lombok.Generated;

@SpringBootApplication
@Import(SmartChoiceDBApplication.class)
@EnableAsync
public class SmartChoiceApiApplication {

  @Generated
  public static void main(String[] args) {
    SpringApplication.run(SmartChoiceApiApplication.class, args);
  }
}
