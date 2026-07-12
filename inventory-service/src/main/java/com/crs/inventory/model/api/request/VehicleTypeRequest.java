package com.crs.inventory.model.api.request;

import static java.util.Locale.ROOT;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record VehicleTypeRequest(
    @NotNull
    @Size(min = 2, max = 50)
    String name,

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    BigDecimal price
) {

  public VehicleTypeRequest {
    name = name == null ? null : name.toUpperCase(ROOT);
  }

}
