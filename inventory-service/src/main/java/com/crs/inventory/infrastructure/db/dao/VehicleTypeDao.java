package com.crs.inventory.infrastructure.db.dao;

import com.crs.inventory.infrastructure.db.mapper.VehicleTypeEntityMapper;
import com.crs.inventory.infrastructure.db.repository.VehicleTypeRepository;
import com.crs.inventory.model.api.request.VehicleTypeRequest;
import com.crs.inventory.model.dto.VehicleType;
import com.crs.inventory.model.dto.VehicleTypeCount;
import com.crs.inventory.model.enums.VehicleStatus;
import com.crs.inventory.model.exception.InvalidDataException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleTypeDao {

  private final VehicleTypeRepository repository;
  private final VehicleTypeEntityMapper mapper;

  public Optional<VehicleTypeCount> getTypeCount(UUID typeUuid, VehicleStatus status) {
    return repository.getTypeCount(typeUuid.toString(), status)
        .map(mapper::toTypeCount);
  }

  public List<VehicleType> getTypes() {
    return mapper.toTypes(repository.findAll());
  }

  public VehicleType create(VehicleTypeRequest request) {
    validateName(request.name());
    var savedEntity = repository.save(mapper.toEntity(request));
    return mapper.toType(savedEntity);
  }

  private void validateName(String name) {
    if (repository.existsByName(name)) {
      throw new InvalidDataException("vehicle type %s already exists".formatted(name));
    }
  }

}
