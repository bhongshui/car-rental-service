package com.crs.inventory.model.dto;

import com.crs.inventory.model.enums.VehicleStatus;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record Vehicle(
    String vehicleId,
    VehicleStatus status,
    LocalDate purchaseDate
) {

}
