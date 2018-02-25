package io.jwg.models;

import com.google.common.collect.ImmutableList;

import java.util.List;

public enum Measurement {
    TSP("TEASPOON"),
    TBSP("TABLESPOON"),
    CUP(),
    FLUID_OUNCE("FLOZ"),
    GRAM("G"),
    OUNCE("OZ"),
    POUND("LB"),
    EACH();

    private final List<String> alternateNames;

    Measurement(String... alternateNames) {
        this.alternateNames = ImmutableList.copyOf(alternateNames);
    }

    public static Measurement fromString(String str) {
        for (Measurement value : values()) {
            if (value.isMeasurement(str)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown measurement: " + str);
    }

    public boolean isMeasurement(String str) {
        return name().equalsIgnoreCase(str) || alternateNames.stream().anyMatch(s -> s.equalsIgnoreCase(str));
    }
}
