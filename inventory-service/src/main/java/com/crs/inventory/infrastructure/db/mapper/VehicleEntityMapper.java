package com.crs.inventory.infrastructure.db.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.crs.inventory.model.api.request.VehicleRequest;
import com.crs.inventory.model.dto.Vehicle;
import com.crs.inventory.model.entity.VehicleEntity;
import com.crs.inventory.model.entity.VehicleTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface VehicleEntityMapper {

  Vehicle toVehicle(VehicleEntity entity);

  default VehicleEntity toEntity(VehicleRequest request, VehicleTypeEntity vehicleType) {
    return VehicleEntity.builder()
        .vehicleId(request.vehicleId())
        .vehicleType(vehicleType)
        .status(request.status())
        .purchaseDate(request.purchaseDate())
        .build();
  }

}
