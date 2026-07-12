package com.crs.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.crs.reservation.model.properties")
public class ReservationsApplication {

  static void main(String[] args) {
    SpringApplication.run(ReservationsApplication.class, args);
  }

}
