package com.crs.reservation.api.mapper;

import static com.crs.reservation.model.ReservationStatus.CREATED;
import static java.time.LocalDateTime.MAX;
import static java.time.LocalDateTime.MIN;
import static org.assertj.core.api.Assertions.assertThat;

import com.crs.reservation.model.Reservation;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ReservationResponseMapperTest {

  private final ReservationResponseMapper mapper = new ReservationResponseMapperImpl();

  @Test
  void shouldMapToResponse() {
    // given
    var reservationUuid = UUID.randomUUID();
    var accountUuid = UUID.randomUUID();
    var vehicleTypeUuid = UUID.randomUUID();

    var reservation = Reservation.builder()
        .reservationUuid(reservationUuid)
        .accountUuid(accountUuid)
        .vehicleTypeUuid(vehicleTypeUuid)
        .reservationFrom(MIN)
        .reservationTo(MAX)
        .status(CREATED)
        .build();

    // when
    var response = mapper.toResponse(reservation);

    // then
    assertThat(response).isNotNull();
    assertThat(response.reservationUuid()).isEqualTo(reservationUuid);
    assertThat(response.accountUuid()).isEqualTo(accountUuid);
    assertThat(response.vehicleTypeUuid()).isEqualTo(vehicleTypeUuid);
    assertThat(response.reservationFrom()).isEqualTo(MIN);
    assertThat(response.reservationTo()).isEqualTo(MAX);
    assertThat(response.status()).isEqualTo(CREATED);
  }

}