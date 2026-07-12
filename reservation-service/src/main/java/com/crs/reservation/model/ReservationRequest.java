package com.crs.reservation.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Builder
public record ReservationRequest(
    @NotNull
    UUID accountUuid,

    @NotNull
    UUID vehicleTypeUuid,

    @NotNull
    @DateTimeFormat(iso = ISO.DATE_TIME)
    LocalDateTime reservationFrom,

    @Min(1)
    @Max(100)
    int durationDays
) {

}
