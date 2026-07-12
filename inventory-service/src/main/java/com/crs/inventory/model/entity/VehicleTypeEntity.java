package com.crs.inventory.model.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "vehicle_type")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  private String typeUuid;

  private String name;

  private BigDecimal price;

  @OneToMany
  @JoinColumn(name = "vehicle_type_id")
  private List<VehicleEntity> vehicles;

}
