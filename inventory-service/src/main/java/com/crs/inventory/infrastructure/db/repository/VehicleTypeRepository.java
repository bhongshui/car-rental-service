package com.crs.inventory.infrastructure.db.repository;

import com.crs.inventory.model.entity.VehicleTypeEntity;
import com.crs.inventory.model.enums.VehicleStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long> {

  /*
    This is not the most efficient solution, but it is good enough for this example.
  */
  @Query("""
      select t from VehicleTypeEntity t
      left join fetch t.vehicles v
      where t.typeUuid = :typeUuid and v.status = :vehicleStatus
      """)
  Optional<VehicleTypeEntity> getTypeCount(String typeUuid, VehicleStatus vehicleStatus);

  Optional<VehicleTypeEntity> findByTypeUuid(String typeUuid);

  boolean existsByName(String name);

}
