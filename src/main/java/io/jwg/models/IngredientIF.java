package io.jwg.models;

import org.immutables.value.Value;

@RecipeStyle
@Value.Immutable
public interface IngredientIF extends IngredientCore {

    int getRecipeId();
}
