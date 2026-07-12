package com.crs.reservation.api.exception;

import static com.crs.reservation.model.exception.ErrorCode.CUSTOM_EXCEPTION;
import static com.crs.reservation.model.exception.ErrorCode.INVALID_ARGUMENT;
import static com.crs.reservation.model.exception.ErrorCode.MISSING_PARAM;
import static com.crs.reservation.model.exception.ErrorCode.NOT_READABLE;
import static com.crs.reservation.model.exception.ErrorCode.NOT_UNIQUE;
import static com.crs.reservation.model.exception.ErrorCode.TYPE_MISMATCH;
import static com.crs.reservation.model.exception.ErrorCode.UNEXPECTED_EXCEPTION;
import static org.hibernate.exception.ConstraintViolationException.ConstraintKind.UNIQUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.crs.reservation.model.exception.CustomException;
import com.crs.reservation.model.exception.ExceptionResponse;
import com.crs.reservation.model.exception.InvalidDataException;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.event.KeyValuePair;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.ValueInstantiationException;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(CustomException.class)
  public ExceptionResponse handleCustomException(CustomException ex) {
    log(ex);
    return new ExceptionResponse(CUSTOM_EXCEPTION, ex.getMessage());
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ExceptionResponse handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
    log(ex);
    var message = "request param '%s' is required".formatted(ex.getParameterName());
    return new ExceptionResponse(MISSING_PARAM, message);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    log(ex);
    var details = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(f -> f.getField() + " " + f.getDefaultMessage())
        .toList();
    return new ExceptionResponse(INVALID_ARGUMENT, details);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ExceptionResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
    log(ex);
    return new ExceptionResponse(TYPE_MISMATCH, createMessage(ex));
  }

  private String createMessage(MethodArgumentTypeMismatchException ex) {
    return switch (ex) {
      case MethodArgumentTypeMismatchException e
          when UUID.class.equals(e.getRequiredType()) -> "path value should be a valid UUID";
      case MethodArgumentTypeMismatchException e
          when e.getCause() instanceof ConversionFailedException c
          && c.getCause() instanceof InvalidDataException i -> i.getMessage();
      default -> ex.getName() + " is invalid";
    };
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    log(ex);
    return switch (ex.getCause()) {
      case InvalidFormatException i when i.getCause() instanceof DateTimeParseException ->
          badRequest("date and time should be in iso format");
      case InvalidFormatException i when i.getMessage().contains("UUID") ->
          badRequest("UUID has to be represented by standard 36-char representation");
      case ValueInstantiationException v when v.getCause() instanceof InvalidDataException i ->
          badRequest(i.getMessage());
      default -> handleException(ex);
    };
  }

  private ResponseEntity<ExceptionResponse> badRequest(String message) {
    return ResponseEntity.badRequest()
        .body(new ExceptionResponse(NOT_READABLE, message));
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ExceptionResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    return (ex.getCause() instanceof ConstraintViolationException c && c.getKind() == UNIQUE)
        ? handleNotUnique(c)
        : handleException(ex);
  }

  private ResponseEntity<ExceptionResponse> handleNotUnique(ConstraintViolationException ex) {
    log(ex);
    return ResponseEntity.badRequest()
        .body(new ExceptionResponse(NOT_UNIQUE, "provided data is not unique"));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
    log(ex);
    return ResponseEntity.internalServerError()
        .body(new ExceptionResponse(UNEXPECTED_EXCEPTION, "unexpected error occurred"));
  }

  private static void log(Exception ex) {
    log.error(
        "Handle exception {}, {}",
        new KeyValuePair("type", ex.getClass().getSimpleName()),
        new KeyValuePair("message", ex.getMessage()),
        ex);
  }

}
