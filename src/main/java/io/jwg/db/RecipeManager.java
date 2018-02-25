package io.jwg.db;

import com.google.inject.Inject;
import io.jwg.models.Ingredient;
import io.jwg.models.IngredientEgg;
import io.jwg.models.Recipe;
import io.jwg.models.RecipeEgg;


public class RecipeManager {

	private RecipeDao dao;
	private IngredientDao ingredientDao;

	@Inject
	public RecipeManager(RecipeDao dao, IngredientDao ingredientDao) {
		this.dao = dao;
		this.ingredientDao = ingredientDao;
	}

	public Recipe getById(int id) {
		return dao.getById(id);
	}

	public Recipe insert(RecipeEgg recipeEgg) {

		int id = dao.insertRecipe(recipeEgg);
		for (IngredientEgg egg : recipeEgg.getIngredients()) {
			ingredientDao.insertIngredient(Ingredient.builder().from(egg).setRecipeId(id).build());
		}
		return dao.getById(id);
	}
}
