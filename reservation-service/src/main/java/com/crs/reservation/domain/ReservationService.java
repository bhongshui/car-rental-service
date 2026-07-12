package com.crs.reservation.domain;

import static java.time.format.DateTimeFormatter.ofPattern;

import com.crs.reservation.infrastructure.client.service.InventoryClientService;
import com.crs.reservation.infrastructure.db.dao.ReservationDao;
import com.crs.reservation.model.Reservation;
import com.crs.reservation.model.ReservationRequest;
import com.crs.reservation.model.exception.BusinessException;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

  private static final DateTimeFormatter FORMATTER = ofPattern("yyyy-MM-dd HH:mm:ss");

  private final InventoryClientService clientService;
  private final ReservationDao dao;

  public Reservation save(ReservationRequest request) {
    var from = request.reservationFrom();
    var to = from.plusDays(request.durationDays());

    var availableVehicles = clientService.getAvailableVehicleTypes(request.vehicleTypeUuid());
    var reservedVehicles = dao.countReservationsInPeriod(request.vehicleTypeUuid(), from, to);

    if (availableVehicles <= reservedVehicles) {
      throw new BusinessException(
          "No vehicles available for reservation from %s to %s"
              .formatted(FORMATTER.format(from), FORMATTER.format(to)));
    }
    return dao.save(request, to);
  }

}
