package io.jwg.models;

import org.immutables.value.Value;

@RecipeStyle
@Value.Immutable
public interface RecipeComponentIF extends RecipeComponentCoreIF {

	int getId();


}
