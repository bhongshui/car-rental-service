package com.crs.reservation.infrastructure.db.repository;

import com.crs.reservation.model.ReservationEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {

  @Query("""
      select count(r) from ReservationEntity r
      where r.vehicleTypeUuid = :typeUuid
      and (r.reservationFrom between :from and :to or r.reservationTo between :from and :to)
      """)
  long countReservationsInPeriod(String typeUuid, LocalDateTime from, LocalDateTime to);

}
