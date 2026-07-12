package com.crs.inventory.api.converter;

import com.crs.inventory.model.enums.VehicleStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.core.convert.converter.Converter;

public class VehicleStatusConverter implements Converter<String, VehicleStatus> {

  @Override
  public VehicleStatus convert(@NonNull String source) {
    return VehicleStatus.fromValue(source);
  }

}