package com.crs.reservation.api.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.crs.reservation.model.Reservation;
import com.crs.reservation.model.ReservationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ReservationResponseMapper {

  ReservationResponse toResponse(Reservation reservation);

}
