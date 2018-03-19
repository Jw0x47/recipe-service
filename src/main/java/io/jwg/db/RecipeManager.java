package io.jwg.db;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import io.jwg.models.*;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.math.Fraction;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


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
			Optional<RecipeComponent> maybeComponent = ingredientDao.getComponentByName(componentName);
			final int componentId;
			if (maybeComponent.isPresent()) {
				componentId = maybeComponent.get().getId();
			} else {
				componentId = ingredientDao.createComponent(componentName);
			}
			ingredientDao.createRecipeEntry(recipeId, componentId, entry.getAmount(), entry.getMeasurement());
		}
	}

	public List<RecipeEntry> getEntriesForRecipes(List<Integer> recipeIds) {
		return recipeDao.getEntriesForRecipes(recipeIds);
	}

	public List<String> getShoppingList(List<Integer> recipeIds) {
		List<RecipeEntry> entries = recipeIds.stream()
				.map(recipeDao::getEntriesForRecipeById)
				.flatMap(List::stream)
				.collect(Collectors.toList());

		Multimap<String, RecipeEntry> entriesMap = Multimaps.index(entries, r -> r.getName() + "_" + r.getMeasurement().name());

		List<RecipeEntry> condensedEntries = entriesMap.asMap().entrySet().stream()
				.map(entry -> {
					RecipeEntry firstEntry = entry.getValue().iterator().next();
					double result = entry.getValue().stream().mapToDouble(RecipeEntry::getAmount).sum();
					return RecipeEntry.builder()
							.from(firstEntry)
							.setAmount(result)
							.build();
				}).collect(Collectors.toList());

		condensedEntries.sort(Comparator.comparing(RecipeEntry::getName));
		return condensedEntries.stream()
				.map(e -> {
					Fraction fraction = Fraction.getFraction(e.getAmount());
					String amount = String.valueOf(fraction.getNumerator());
					if (fraction.getDenominator() != 1) {
						amount += "/" + String.valueOf(fraction.getDenominator());
					}
					return String.format("%s %s %s", amount, e.getMeasurement(), e.getName());
				})
				.collect(Collectors.toList());
	}
}
