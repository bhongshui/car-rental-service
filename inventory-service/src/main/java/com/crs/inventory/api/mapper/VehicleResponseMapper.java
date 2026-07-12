package com.crs.inventory.api.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.crs.inventory.model.api.response.VehicleResponse;
import com.crs.inventory.model.dto.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface VehicleResponseMapper {

  VehicleResponse toResponse(Vehicle vehicle);

}
