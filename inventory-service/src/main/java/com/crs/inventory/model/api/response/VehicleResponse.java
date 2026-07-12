package com.crs.inventory.model.api.response;

import com.crs.inventory.model.enums.VehicleStatus;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record VehicleResponse(
    String vehicleId,
    VehicleStatus status,
    LocalDate purchaseDate
) {

}
