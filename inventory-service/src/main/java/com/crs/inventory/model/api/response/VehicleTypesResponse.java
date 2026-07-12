package com.crs.inventory.model.api.response;

import java.util.List;
import lombok.Builder;

@Builder
public record VehicleTypesResponse(List<VehicleTypeResponse> vehicleTypes) {
}
