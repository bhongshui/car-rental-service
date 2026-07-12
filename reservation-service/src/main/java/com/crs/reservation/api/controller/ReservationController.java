package com.crs.reservation.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.crs.reservation.api.mapper.ReservationResponseMapper;
import com.crs.reservation.domain.ReservationService;
import com.crs.reservation.model.ReservationRequest;
import com.crs.reservation.model.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationService service;
  private final ReservationResponseMapper mapper;

  @PostMapping
  @ResponseStatus(CREATED)
  public ReservationResponse save(@RequestBody @Valid ReservationRequest request) {
    return mapper.toResponse(service.save(request));
  }

}
