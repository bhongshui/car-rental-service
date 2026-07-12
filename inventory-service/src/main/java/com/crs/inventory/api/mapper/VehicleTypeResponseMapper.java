package com.crs.inventory.api.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.crs.inventory.model.api.response.VehicleTypeCountResponse;
import com.crs.inventory.model.api.response.VehicleTypeResponse;
import com.crs.inventory.model.api.response.VehicleTypesResponse;
import com.crs.inventory.model.dto.VehicleType;
import com.crs.inventory.model.dto.VehicleTypeCount;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface VehicleTypeResponseMapper {

  VehicleTypeCountResponse toResponse(VehicleTypeCount typeCount);

  default VehicleTypesResponse toResponse(List<VehicleType> types) {
    return new VehicleTypesResponse(toResponses(types));
  }

  List<VehicleTypeResponse> toResponses(List<VehicleType> types);

  VehicleTypeResponse toResponse(VehicleType type);

}
