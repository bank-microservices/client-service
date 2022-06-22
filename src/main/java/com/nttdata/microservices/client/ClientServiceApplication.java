package com.nttdata.microservices.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class ClientServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientServiceApplication.class, args);
  }

}
