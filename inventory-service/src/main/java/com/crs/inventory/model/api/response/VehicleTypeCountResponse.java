package com.crs.inventory.model.api.response;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record VehicleTypeCountResponse(
    String name,
    BigDecimal price,
    long amount
) {

}
