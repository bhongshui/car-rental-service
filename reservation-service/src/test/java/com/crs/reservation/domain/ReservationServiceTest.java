package com.crs.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.crs.reservation.infrastructure.client.service.InventoryClientService;
import com.crs.reservation.infrastructure.db.dao.ReservationDao;
import com.crs.reservation.model.Reservation;
import com.crs.reservation.model.ReservationRequest;
import com.crs.reservation.model.exception.BusinessException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  @Mock
  private InventoryClientService clientService;

  @Mock
  private ReservationDao dao;

  @InjectMocks
  private ReservationService service;

  @Test
  void shouldCalculateDateTo() {
    // given
    var from = LocalDateTime.of(2026, 1, 30, 0, 0, 1, 1);
    var expected = LocalDateTime.of(2026, 2, 2, 0, 0, 1, 1);
    var captor = ArgumentCaptor.forClass(LocalDateTime.class);

    var request = ReservationRequest.builder()
        .reservationFrom(from)
        .durationDays(3)
        .build();

    when(clientService.getAvailableVehicleTypes(any())).thenReturn(10L);
    when(dao.countReservationsInPeriod(any(), any(), captor.capture())).thenReturn(0L);

    // when
    service.save(request);

    // then
    assertThat(captor.getValue()).isEqualTo(expected);
  }

  private static Stream<Arguments> notEnoughAmounts() {
    return Stream.of(
        Arguments.of(0, 0),
        Arguments.of(1, 1),
        Arguments.of(1, 2)
    );
  }

  @MethodSource("notEnoughAmounts")
  @ParameterizedTest
  void shouldThrowExceptionThenNoVehiclesAvailableInProvidedPeriod(long available, long reserved) {
    // given
    var from = LocalDateTime.of(2026, 1, 30, 0, 0, 1, 1);
    var typeUuid = UUID.randomUUID();

    var request = ReservationRequest.builder()
        .reservationFrom(from)
        .durationDays(3)
        .vehicleTypeUuid(typeUuid)
        .build();

    when(clientService.getAvailableVehicleTypes(typeUuid)).thenReturn(available);
    when(dao.countReservationsInPeriod(eq(typeUuid), eq(from), any())).thenReturn(reserved);

    // when
    // then
    assertThatThrownBy(() -> service.save(request))
        .isExactlyInstanceOf(BusinessException.class)
        .hasMessageStartingWith("No vehicles available for reservation from 2026-01-30 00:00:01 to ");
  }

  private static Stream<Arguments> enoughAmounts() {
    return Stream.of(
        Arguments.of(1, 0),
        Arguments.of(2, 1)
    );
  }

  @MethodSource("enoughAmounts")
  @ParameterizedTest
  void shouldSaveReservationWhenVehiclesAvailableInProvidedPeriod(long available, long reserved) {
    // given
    var from = LocalDateTime.of(2026, 1, 30, 0, 0, 1, 1);
    var typeUuid = UUID.randomUUID();
    var expected = mock(Reservation.class);

    var request = ReservationRequest.builder()
        .reservationFrom(from)
        .durationDays(3)
        .vehicleTypeUuid(typeUuid)
        .build();

    when(clientService.getAvailableVehicleTypes(typeUuid)).thenReturn(available);
    when(dao.countReservationsInPeriod(eq(typeUuid), eq(from), any())).thenReturn(reserved);
    when(dao.save(eq(request), any())).thenReturn(expected);

    // when
    var actual = service.save(request);

    // then
    assertThat(actual).isSameAs(expected);
  }

}