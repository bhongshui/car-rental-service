package com.crs.reservation.config;

import com.crs.reservation.infrastructure.client.exchange.InventoryClient;
import com.crs.reservation.model.properties.HttpClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

  @Bean
  public InventoryClient inventoryClient(HttpClientConfiguration clientConfig) {

    var restClient = RestClient.builder()
        .baseUrl(clientConfig.inventoryClient().baseUrl())
        .build();

    return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
        .build()
        .createClient(InventoryClient.class);
  }

}
