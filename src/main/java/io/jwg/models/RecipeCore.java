package io.jwg.models;

import java.util.List;

public interface RecipeCore {

    String getName();
    String getLink();
    int getCalories();
    List<IngredientEgg> getIngredients();

}
