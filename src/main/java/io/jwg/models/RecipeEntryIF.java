package io.jwg.models;

import org.immutables.value.Value;

@RecipeStyle
@Value.Immutable
public interface RecipeEntryIF {

	String getName();

	double getAmount();

	Measurement getMeasurement();

	@Value.Default
	default String getMeasurementFormatted() {
		return getMeasurement().toString();
	}


}
