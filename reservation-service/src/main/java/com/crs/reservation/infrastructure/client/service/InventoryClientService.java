package com.crs.reservation.infrastructure.client.service;

import com.crs.reservation.infrastructure.client.exchange.InventoryClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryClientService {

  private final InventoryClient client;

  public long getAvailableVehicleTypes(UUID typeUuid) {
    return client.getTypeCount(typeUuid, "available").amount();
  }

}
