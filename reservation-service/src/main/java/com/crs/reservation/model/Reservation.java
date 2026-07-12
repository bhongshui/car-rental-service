package com.crs.reservation.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder
public record Reservation(
    UUID reservationUuid,
    UUID accountUuid,
    UUID vehicleTypeUuid,
    LocalDateTime reservationFrom,
    LocalDateTime reservationTo,
    ReservationStatus status
) {

}
