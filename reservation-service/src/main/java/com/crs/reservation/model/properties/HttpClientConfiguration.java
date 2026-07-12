package com.crs.reservation.model.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http-clients")
public record HttpClientConfiguration(HttpClientProperties inventoryClient) {

  public record HttpClientProperties(String baseUrl) {

  }

}
