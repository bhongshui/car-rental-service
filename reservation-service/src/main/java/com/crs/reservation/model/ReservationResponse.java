package com.crs.reservation.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
    UUID reservationUuid,
    UUID accountUuid,
    UUID vehicleTypeUuid,
    LocalDateTime reservationFrom,
    LocalDateTime reservationTo,
    ReservationStatus status
) {

}
