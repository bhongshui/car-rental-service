package com.crs.reservation.infrastructure.db.mapper;

import static com.crs.reservation.model.ReservationStatus.CREATED;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.crs.reservation.model.Reservation;
import com.crs.reservation.model.ReservationEntity;
import com.crs.reservation.model.ReservationRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ReservationEntityMapper {

  default ReservationEntity toEntity(ReservationRequest request, LocalDateTime to) {
    return ReservationEntity.builder()
        .reservationUuid(UUID.randomUUID().toString())
        .accountUuid(request.accountUuid().toString())
        .vehicleTypeUuid(request.vehicleTypeUuid().toString())
        .reservationFrom(request.reservationFrom())
        .reservationTo(to)
        .status(CREATED)
        .build();
  }

  Reservation toReservation(ReservationEntity savedEntity);

}
