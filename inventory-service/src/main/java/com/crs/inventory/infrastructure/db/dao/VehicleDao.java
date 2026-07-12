package com.crs.inventory.infrastructure.db.dao;

import com.crs.inventory.infrastructure.db.mapper.VehicleEntityMapper;
import com.crs.inventory.infrastructure.db.repository.VehicleRepository;
import com.crs.inventory.infrastructure.db.repository.VehicleTypeRepository;
import com.crs.inventory.model.api.request.VehicleRequest;
import com.crs.inventory.model.dto.Vehicle;
import com.crs.inventory.model.entity.VehicleEntity;
import com.crs.inventory.model.entity.VehicleTypeEntity;
import com.crs.inventory.model.exception.InvalidDataException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDao {

  private final VehicleTypeRepository typeRepository;
  private final VehicleRepository repository;
  private final VehicleEntityMapper mapper;

  public Vehicle save(UUID typeUuid, VehicleRequest request) {
    var vehicleType = typeRepository.findByTypeUuid(typeUuid.toString());
    if (vehicleType.isEmpty()) {
      throw new InvalidDataException("vehicle type %s does not exist".formatted(typeUuid));
    }
    var entity = modifyOrCreate(request, vehicleType.get());
    return mapper.toVehicle(repository.save(entity));
  }

  private VehicleEntity modifyOrCreate(VehicleRequest request, VehicleTypeEntity vehicleType) {
    return repository.findByVehicleId(request.vehicleId())
        .map(entity -> {
          entity.setStatus(request.status());
          entity.setPurchaseDate(request.purchaseDate());
          entity.setVehicleType(vehicleType);
          return entity;
        })
        .orElseGet(() -> mapper.toEntity(request, vehicleType));
  }

}
