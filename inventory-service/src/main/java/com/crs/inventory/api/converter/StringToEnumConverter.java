package com.crs.inventory.api.converter;

import static java.util.Locale.ROOT;

import com.crs.inventory.model.exception.InvalidDataException;
import java.util.Arrays;
import java.util.function.Function;

public class StringToEnumConverter {

  public static <T extends Enum<?>> T convert(String value, Function<String, T> valueOf, T[] values) {
    try {
      return value == null ? null : valueOf.apply(value.toUpperCase(ROOT));
    } catch (IllegalArgumentException ex) {
      throw new InvalidDataException(
          "'%s' is not valid value, should be one of the following: %s"
              .formatted(value, Arrays.toString(values)));
    }
  }

}
