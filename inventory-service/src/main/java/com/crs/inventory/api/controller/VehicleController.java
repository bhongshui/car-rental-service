package com.crs.inventory.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.crs.inventory.api.mapper.VehicleResponseMapper;
import com.crs.inventory.domain.VehicleService;
import com.crs.inventory.model.api.request.VehicleRequest;
import com.crs.inventory.model.api.response.VehicleResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vehicle-types/{typeUuid}/vehicles")
@RequiredArgsConstructor
public class VehicleController {

  private final VehicleService service;
  private final VehicleResponseMapper mapper;

  /*
    For the sake of an example the assumption is that during purchase process each vehicle is given
    unique id that we decided to use as a resource identifier.
   */
  @PutMapping
  @ResponseStatus(CREATED)
  public VehicleResponse save(@PathVariable UUID typeUuid, @RequestBody @Valid VehicleRequest request) {
    return mapper.toResponse(service.save(typeUuid, request));
  }

}
