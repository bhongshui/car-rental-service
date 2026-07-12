package com.crs.inventory.api.controller;

import static com.crs.inventory.model.enums.VehicleStatus.AVAILABLE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.crs.inventory.api.mapper.VehicleTypeResponseMapper;
import com.crs.inventory.api.mapper.VehicleTypeResponseMapperImpl;
import com.crs.inventory.domain.VehicleTypeService;
import com.crs.inventory.model.api.request.VehicleTypeRequest;
import com.crs.inventory.model.dto.VehicleType;
import com.crs.inventory.model.dto.VehicleTypeCount;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VehicleTypeController.class)
@Import(VehicleTypeResponseMapperImpl.class)
class VehicleTypeControllerTest {

  private static final UUID TYPE_UUID = UUID.randomUUID();

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private VehicleTypeService service;

  @Autowired
  private VehicleTypeResponseMapper mapper;

  @Nested
  @DisplayName("GET /vehicle-types/{typeUuid}")
  class GetVehicleTypeCountTest {

    private static final String PATH = "/vehicle-types/{typeUuid}";
    private static final String PARAM = "status";
    private static final String PARAM_VALID_VALUE = "available";

    @Test
    void shouldFailOnMissingRequestParam() throws Exception {
      // given
      // when
      mockMvc.perform(
              get(PATH, TYPE_UUID)
                  .contentType(APPLICATION_JSON))
          // then
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details", hasSize(1)))
          .andExpectAll(
              jsonPath("$.errorCode", equalTo("CRS02")),
              jsonPath("$.details[0]", equalTo("request param 'status' is required"))
          );
    }

    @Test
    void shouldFailOnInvalidRequestParam() throws Exception {
      // given
      // when
      mockMvc.perform(
              get(PATH, TYPE_UUID)
                  .contentType(APPLICATION_JSON)
                  .param(PARAM, "abc"))
          // then
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details", hasSize(1)))
          .andExpectAll(
              jsonPath("$.errorCode", equalTo("CRS04")),
              jsonPath(
                  "$.details[0]", equalTo(
                      "'abc' is not valid value, should be one of the following: [AVAILABLE, DECOMMISSIONED]"))
          );
    }

    @Test
    void shouldFailOnInvalidPathUuid() throws Exception {
      // given
      // when
      mockMvc.perform(
              get(PATH, "invalidUuid")
                  .contentType(APPLICATION_JSON)
                  .param(PARAM, PARAM_VALID_VALUE))
          // then
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details", hasSize(1)))
          .andExpectAll(
              jsonPath("$.errorCode", equalTo("CRS04")),
              jsonPath("$.details[0]", equalTo("path value should be a valid UUID"))
          );
    }

    @Test
    void shouldReturnNoContentWhenNoCountFound() throws Exception {
      // given
      when(service.getTypeCount(TYPE_UUID, AVAILABLE)).thenReturn(Optional.empty());

      // when
      mockMvc.perform(
              get(PATH, TYPE_UUID)
                  .contentType(APPLICATION_JSON)
                  .param(PARAM, PARAM_VALID_VALUE))
          // then
          .andExpect(status().isNoContent())
          .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldReturnOkWhenFoundCountForType() throws Exception {
      // given
      var typeCount = VehicleTypeCount.builder()
          .typeUuid(TYPE_UUID)
          .name("SUV")
          .price(TEN)
          .amount(20)
          .build();

      when(service.getTypeCount(TYPE_UUID, AVAILABLE)).thenReturn(Optional.of(typeCount));

      // when
      mockMvc.perform(
              get(PATH, TYPE_UUID)
                  .contentType(APPLICATION_JSON)
                  .param(PARAM, PARAM_VALID_VALUE))
          // then
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.name", equalTo("SUV")))
          .andExpect(jsonPath("$.price", equalTo(10)))
          .andExpect(jsonPath("$.amount", equalTo(20)));
    }

  }

  @Nested
  @DisplayName("POST /vehicle-types")
  class PostVehicleTypesTest {

    private static final String PATH = "/vehicle-types";

    private static Stream<Arguments> invalidRequestBody() {
      return Stream.of(
          Arguments.of("{\"price\": 400.00}", "name must not be null"),
          Arguments.of("{\"name\": null,\"price\": 400.00}", "name must not be null"),
          Arguments.of("{\"name\": \"\",\"price\": 400.00}", "name size must be between 2 and 50"),
          Arguments.of("{\"name\": \"i\",\"price\": 400.00}", "name size must be between 2 and 50"),
          Arguments.of("{\"name\": \"suv\"}", "price must not be null"),
          Arguments.of("{\"name\": \"suv\",\"price\": null}", "price must not be null"),
          Arguments.of("{\"name\": \"suv\",\"price\": -1}", "price must be greater than 0"),
          Arguments.of("{\"name\": \"suv\",\"price\": 0}", "price must be greater than 0"),
          Arguments.of(
              "{\"name\": \"suv\",\"price\": 10.111}",
              "price numeric value out of bounds (<10 digits>.<2 digits> expected)"),
          Arguments.of(
              "{\"name\": \"suv\",\"price\": 12345678901.11}",
              "price numeric value out of bounds (<10 digits>.<2 digits> expected)")
      );
    }

    @ParameterizedTest
    @MethodSource("invalidRequestBody")
    void shouldFailOnInvalidRequestBody(String body, String errorMessage) throws Exception {
      // given
      // when
      mockMvc.perform(
              post(PATH)
                  .contentType(APPLICATION_JSON)
                  .content(body))
          // then
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.details", hasSize(1)))
          .andExpect(jsonPath("$.details[0]", equalTo(errorMessage)));
    }

    @Test
    void shouldConvertNameToUpperCase() throws Exception {
      // given
      var captor = ArgumentCaptor.forClass(VehicleTypeRequest.class);
      when(service.create(captor.capture())).thenReturn(mock(VehicleType.class));

      // when
      mockMvc.perform(
              post(PATH)
                  .contentType(APPLICATION_JSON)
                  .content("{\"name\": \"suv\",\"price\": 400.00}"))
          // then
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.errorCode").doesNotExist());

      assertThat(captor.getValue().name()).isEqualTo("SUV");
    }

    @Test
    void shouldSaveRequest() throws Exception {
      // given
      var vehicleType = VehicleType.builder()
          .typeUuid(TYPE_UUID)
          .name("SUV")
          .price(new BigDecimal("400.00"))
          .build();

      when(service.create(any())).thenReturn(vehicleType);

      // when
      mockMvc.perform(
              post(PATH)
                  .contentType(APPLICATION_JSON)
                  .content("{\"name\": \"SUV\",\"price\": 400}"))
          // then
          .andExpect(status().isCreated())
          .andExpectAll(
              jsonPath("$.typeUuid", equalTo(TYPE_UUID.toString())),
              jsonPath("$.name", equalTo("SUV")),
              jsonPath("$.price", equalTo(400.00))
          );
    }

  }

}