package com.crs.inventory.api.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.crs.inventory.api.mapper.VehicleTypeResponseMapper;
import com.crs.inventory.domain.VehicleTypeService;
import com.crs.inventory.model.api.request.VehicleTypeRequest;
import com.crs.inventory.model.api.response.VehicleTypeCountResponse;
import com.crs.inventory.model.api.response.VehicleTypeResponse;
import com.crs.inventory.model.api.response.VehicleTypesResponse;
import com.crs.inventory.model.enums.VehicleStatus;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vehicle-types")
@RequiredArgsConstructor
public class VehicleTypeController {

  private final VehicleTypeService service;
  private final VehicleTypeResponseMapper mapper;

  @GetMapping("/{typeUuid}")
  public ResponseEntity<VehicleTypeCountResponse> getTypeCount(
      @PathVariable UUID typeUuid, @RequestParam VehicleStatus status) {
    return service.getTypeCount(typeUuid, status)
        .map(mapper::toResponse)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.noContent().build());
  }

  @GetMapping
  public ResponseEntity<VehicleTypesResponse> getTypes() {
    var vehicleTypes = service.getTypes();
    return vehicleTypes.isEmpty()
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(mapper.toResponse(vehicleTypes));
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public VehicleTypeResponse create(@RequestBody @Valid VehicleTypeRequest request) {
    return mapper.toResponse(service.create(request));
  }

}
