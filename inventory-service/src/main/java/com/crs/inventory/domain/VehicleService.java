package com.crs.inventory.domain;

import com.crs.inventory.infrastructure.db.dao.VehicleDao;
import com.crs.inventory.model.api.request.VehicleRequest;
import com.crs.inventory.model.dto.Vehicle;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

  private final VehicleDao dao;

  public Vehicle save(UUID typeUuid, VehicleRequest request) {
    return dao.save(typeUuid, request);
  }

}
