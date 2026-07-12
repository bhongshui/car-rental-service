package com.crs.inventory.model.exception;

import java.util.List;

public record ExceptionResponse(ErrorCode errorCode, List<String> details) {

  public ExceptionResponse(ErrorCode errorCode, String details) {
    this(errorCode, List.of(details));
  }

}
