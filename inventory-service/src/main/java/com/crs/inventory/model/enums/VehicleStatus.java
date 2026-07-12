package com.crs.inventory.model.enums;

import static com.crs.inventory.api.converter.StringToEnumConverter.convert;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum VehicleStatus {
  AVAILABLE,
  DECOMMISSIONED;

  @JsonCreator
  public static VehicleStatus fromValue(String value) {
    return convert(value, VehicleStatus::valueOf, VehicleStatus.values());
  }

}
