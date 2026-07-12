package com.crs.inventory.model.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
  CUSTOM_EXCEPTION("CRS01"),
  MISSING_PARAM("CRS02"),
  INVALID_ARGUMENT("CRS03"),
  TYPE_MISMATCH("CRS04"),
  NOT_READABLE("CRS05"),
  NOT_UNIQUE("CRS06"),
  UNEXPECTED_EXCEPTION("CRS07");

  private final String errorCode;

  @JsonValue
  public String errorCode() {
    return errorCode;
  }

}
