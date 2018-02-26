package io.jwg.db;

import com.google.inject.Inject;
import io.jwg.models.*;
import org.apache.commons.lang.WordUtils;

import javax.transaction.Transactional;
import java.util.List;


public class RecipeManager {

	private RecipeDao recipeDao;
	private IngredientDao ingredientDao;

	@Inject
	public RecipeManager(RecipeDao dao, IngredientDao ingredientDao) {
		this.recipeDao = dao;
		this.ingredientDao = ingredientDao;
	}

	public List<Recipe> listRecipes() {
		return recipeDao.listRecipes();
	}

	public Recipe getById(int id) {
		return recipeDao.getById(id);
	}

	public RecipeWithEntries getByIdWithEntries(int id) {
		Recipe recipe = getById(id);
		List<RecipeEntry> entries = recipeDao.getEntriesForRecipeById(id);
		return RecipeWithEntries.builder()
				.setId(recipe.getId())
				.setLink(recipe.getLink())
				.setName(recipe.getName())
				.setEntries(entries)
				.build();
	}

	@Transactional
	public Recipe insert(RecipeEggWithEntries eggWithEntries) {
		RecipeEgg recipeEgg = RecipeEgg.builder()
				.setLink(eggWithEntries.getLink())
				.setName(eggWithEntries.getName())
				.build();
		int recipeId = recipeDao.insertRecipe(recipeEgg);
		addRecipeEntriesForRecipe(recipeId, eggWithEntries.getEntries());
		return recipeDao.getById(recipeId);
	}

	@Transactional
	public RecipeWithEntries update(int recipeId, RecipeEggWithEntries eggWithEntries) {
		Recipe recipe = Recipe.builder()
				.setId(recipeId)
				.setLink(eggWithEntries.getLink())
				.setName(eggWithEntries.getName())
				.build();
		recipeDao.deleteEntriesById(recipeId);
		recipeDao.updateRecipe(recipe);
		addRecipeEntriesForRecipe(recipeId, eggWithEntries.getEntries());

		return getByIdWithEntries(recipeId);
	}

	private void addRecipeEntriesForRecipe(int recipeId, List<RecipeEntry> entries) {
		for (RecipeEntry entry : entries) {
			String componentName = WordUtils.capitalizeFully(entry.getName().trim());
			int componentId = ingredientDao.createComponent(componentName);
			ingredientDao.createRecipeEntry(recipeId, componentId, entry.getAmount(), entry.getMeasurement());
		}
	}

	public List<RecipeEntry> getEntriesForRecipes(List<Integer> recipeIds) {
		return recipeDao.getEntriesForRecipes(recipeIds);
	}
}
