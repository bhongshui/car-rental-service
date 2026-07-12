package com.crs.inventory.model.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import com.crs.inventory.model.enums.VehicleStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "vehicle")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String vehicleId;

  @ManyToOne
  private VehicleTypeEntity vehicleType;

  @Enumerated(STRING)
  public VehicleStatus status;

  public LocalDate purchaseDate;

}
