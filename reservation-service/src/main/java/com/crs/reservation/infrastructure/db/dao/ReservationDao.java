package com.crs.reservation.infrastructure.db.dao;

import com.crs.reservation.infrastructure.db.mapper.ReservationEntityMapper;
import com.crs.reservation.infrastructure.db.repository.ReservationRepository;
import com.crs.reservation.model.Reservation;
import com.crs.reservation.model.ReservationRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationDao {

  private final ReservationRepository repository;
  private final ReservationEntityMapper mapper;

  public Reservation save(ReservationRequest request, LocalDateTime to) {
    var savedEntity = repository.save(mapper.toEntity(request, to));
    return mapper.toReservation(savedEntity);
  }

  public long countReservationsInPeriod(UUID typeUuid, LocalDateTime from, LocalDateTime to) {
    return repository.countReservationsInPeriod(typeUuid.toString(), from, to);
  }

}
