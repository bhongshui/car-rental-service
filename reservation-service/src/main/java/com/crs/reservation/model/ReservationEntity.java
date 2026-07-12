package com.crs.reservation.model;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "reservation")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false, length = 36)
  private String reservationUuid;

  @Column(nullable = false, length = 36)
  private String accountUuid;

  @Column(nullable = false, length = 36)
  private String vehicleTypeUuid;

  @Column(nullable = false)
  private LocalDateTime reservationFrom;

  @Column(nullable = false)
  private LocalDateTime reservationTo;

  @Enumerated(STRING)
  @Column(nullable = false)
  private ReservationStatus status;

}
