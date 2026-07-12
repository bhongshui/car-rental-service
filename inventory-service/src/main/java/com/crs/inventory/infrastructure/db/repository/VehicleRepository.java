package com.crs.inventory.infrastructure.db.repository;

import com.crs.inventory.model.entity.VehicleEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface VehicleRepository extends CrudRepository<VehicleEntity, Long> {

  Optional<VehicleEntity> findByVehicleId(String vehicleId);

}
