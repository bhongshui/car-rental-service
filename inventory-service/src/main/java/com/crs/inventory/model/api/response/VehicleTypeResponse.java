package com.crs.inventory.model.api.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record VehicleTypeResponse(
    UUID typeUuid,
    String name,
    BigDecimal price
) {

}
