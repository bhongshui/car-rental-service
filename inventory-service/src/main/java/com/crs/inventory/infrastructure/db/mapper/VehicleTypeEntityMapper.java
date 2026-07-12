package com.crs.inventory.infrastructure.db.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.crs.inventory.model.api.request.VehicleTypeRequest;
import com.crs.inventory.model.dto.VehicleType;
import com.crs.inventory.model.dto.VehicleTypeCount;
import com.crs.inventory.model.entity.VehicleTypeEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface VehicleTypeEntityMapper {

  @Mapping(target = "amount", expression = "java(entity.getVehicles().size())")
  VehicleTypeCount toTypeCount(VehicleTypeEntity entity);

  List<VehicleType> toTypes(List<VehicleTypeEntity> entities);

  VehicleType toType(VehicleTypeEntity entity);

  @Mapping(target = "typeUuid", expression = "java(UUID.randomUUID().toString())")
  VehicleTypeEntity toEntity(VehicleTypeRequest request);

}
