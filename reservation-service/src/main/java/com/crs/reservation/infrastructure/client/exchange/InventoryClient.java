package com.crs.reservation.infrastructure.client.exchange;

import com.crs.reservation.model.TypeCountClientResponse;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface InventoryClient {

  @GetExchange("/vehicle-types/{typeUuid}")
  TypeCountClientResponse getTypeCount(@PathVariable UUID typeUuid, @RequestParam String status);

}
