package com.crs.inventory.model.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record VehicleType(
    UUID typeUuid,
    String name,
    BigDecimal price
) {

}
