package com.crs.inventory.infrastructure.db.dao;

import static com.crs.inventory.model.enums.VehicleStatus.AVAILABLE;
import static com.crs.inventory.model.enums.VehicleStatus.DECOMMISSIONED;
import static java.time.LocalDate.MAX;
import static java.time.LocalDate.MIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.crs.inventory.infrastructure.db.mapper.VehicleEntityMapper;
import com.crs.inventory.infrastructure.db.repository.VehicleRepository;
import com.crs.inventory.infrastructure.db.repository.VehicleTypeRepository;
import com.crs.inventory.model.api.request.VehicleRequest;
import com.crs.inventory.model.dto.Vehicle;
import com.crs.inventory.model.entity.VehicleEntity;
import com.crs.inventory.model.entity.VehicleTypeEntity;
import com.crs.inventory.model.exception.InvalidDataException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleDaoTest {

  private static final UUID TYPE_UUID = UUID.randomUUID();

  @Mock
  private VehicleTypeRepository typeRepository;

  @Mock
  private VehicleRepository repository;

  @Mock
  private VehicleEntityMapper mapper;

  @InjectMocks
  private VehicleDao dao;

  @Test
  void shouldThrowExceptionWhenTypeDoesNotExist() {
    // given
    var request = mock(VehicleRequest.class);
    when(typeRepository.findByTypeUuid(TYPE_UUID.toString())).thenReturn(Optional.empty());

    // when
    // then
    assertThatThrownBy(() -> dao.save(TYPE_UUID, request))
        .isExactlyInstanceOf(InvalidDataException.class)
        .hasMessage("vehicle type " + TYPE_UUID + " does not exist");
  }

  @Test
  void shouldCreateNewEntity() {
    // given
    var vehicleId = "someId";
    var request = VehicleRequest.builder().vehicleId(vehicleId).build();
    var typeEntity = mock(VehicleTypeEntity.class);
    var entityToSave = mock(VehicleEntity.class);
    var savedEntity = mock(VehicleEntity.class);
    var expected = mock(Vehicle.class);

    when(typeRepository.findByTypeUuid(TYPE_UUID.toString())).thenReturn(Optional.of(typeEntity));
    when(repository.findByVehicleId(vehicleId)).thenReturn(Optional.empty());
    when(mapper.toEntity(request, typeEntity)).thenReturn(entityToSave);
    when(repository.save(entityToSave)).thenReturn(savedEntity);
    when(mapper.toVehicle(savedEntity)).thenReturn(expected);

    // when
    var actual = dao.save(TYPE_UUID, request);

    // then
    assertThat(actual).isSameAs(expected);
  }

  @Test
  void shouldModifyExistingEntity() {
    // given
    var vehicleId = "someId";
    var typeEntity = mock(VehicleTypeEntity.class);
    var savedEntity = mock(VehicleEntity.class);
    var expected = mock(Vehicle.class);
    var captor = ArgumentCaptor.forClass(VehicleEntity.class);

    var request = VehicleRequest.builder()
        .vehicleId(vehicleId)
        .status(DECOMMISSIONED)
        .purchaseDate(MAX)
        .build();

    var existingEntity = VehicleEntity.builder()
        .vehicleId(vehicleId)
        .vehicleType(mock(VehicleTypeEntity.class))
        .status(AVAILABLE)
        .purchaseDate(MIN)
        .build();

    when(typeRepository.findByTypeUuid(TYPE_UUID.toString())).thenReturn(Optional.of(typeEntity));
    when(repository.findByVehicleId(vehicleId)).thenReturn(Optional.of(existingEntity));
    when(repository.save(captor.capture())).thenReturn(savedEntity);
    when(mapper.toVehicle(savedEntity)).thenReturn(expected);

    // when
    var actual = dao.save(TYPE_UUID, request);

    // then
    assertThat(actual).isSameAs(expected);
    verify(mapper, never()).toEntity(any(), any());

    var entityToSave = captor.getValue();
    assertThat(entityToSave.getVehicleId()).isEqualTo(vehicleId);
    assertThat(entityToSave.getVehicleType()).isSameAs(typeEntity);
    assertThat(entityToSave.getStatus()).isEqualTo(DECOMMISSIONED);
    assertThat(entityToSave.getPurchaseDate()).isEqualTo(MAX);
  }

}