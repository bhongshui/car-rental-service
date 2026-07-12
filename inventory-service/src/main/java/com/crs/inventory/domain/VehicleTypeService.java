package com.crs.inventory.domain;

import com.crs.inventory.infrastructure.db.dao.VehicleTypeDao;
import com.crs.inventory.model.api.request.VehicleTypeRequest;
import com.crs.inventory.model.dto.VehicleType;
import com.crs.inventory.model.dto.VehicleTypeCount;
import com.crs.inventory.model.enums.VehicleStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleTypeService {

  private final VehicleTypeDao dao;

  public Optional<VehicleTypeCount> getTypeCount(UUID typeUuid, VehicleStatus status) {
    return dao.getTypeCount(typeUuid, status);
  }

  public List<VehicleType> getTypes() {
    return dao.getTypes();
  }

  public VehicleType create(VehicleTypeRequest request) {
    return dao.create(request);
  }

}
