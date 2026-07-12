package com.crs.inventory.model.api.request;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

import com.crs.inventory.model.enums.VehicleStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
public record VehicleRequest(
    @NotNull
    @Size(min = 3, max = 40)
    String vehicleId,

    @NotNull
    @DateTimeFormat(iso = ISO.DATE)
    LocalDate purchaseDate,

    @NotNull
    VehicleStatus status
) {

}
