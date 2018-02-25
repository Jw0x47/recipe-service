package io.jwg.models;

import org.immutables.value.Value;

@RecipeStyle
@Value.Immutable
public interface RecipeIF extends RecipeCore {

	int getId();
}
