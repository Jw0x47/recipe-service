package io.jwg.db;

import com.hubspot.rosetta.jdbi.BindWithRosetta;
import io.jwg.models.Recipe;
import io.jwg.models.RecipeEgg;
import io.jwg.models.RecipeEntry;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

@UseStringTemplate3StatementLocator
public interface RecipeDao {

	String RECIPES_JOIN_TO_COMPONENTS = "recipes JOIN recipe_entries ON (recipes.id = recipe_entries.recipeId) " +
			"JOIN components ON (recipe_entries.ingredientId = components.id) ";

	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO recipes (name, link) values (:name, :link)")
	int insertRecipe(@BindWithRosetta RecipeEgg recipe);

	@SqlQuery("SELECT * FROM recipes")
	List<Recipe> listRecipes();

	@SqlQuery("SELECT recipes.*," +
			"components.name, " +
			"recipe_entries.amount, recipe_entries.measurement " +
			"FROM " + RECIPES_JOIN_TO_COMPONENTS +
			"WHERE recipes.id = :id")
	Recipe getById(@Bind("id") int id);

	@SqlQuery("SELECT " +
			"components.id, components.name, " +
			"recipe_entries.amount, recipe_entries.measurement " +
			"FROM " + RECIPES_JOIN_TO_COMPONENTS +
			"WHERE recipes.id = :id")
	List<RecipeEntry> getEntriesForRecipeById(@Bind("id") int id);

	@SqlQuery("SELECT " +
			"components.id, components.name, " +
			"recipe_entries.amount, recipe_entries.measurement " +
			"FROM " + RECIPES_JOIN_TO_COMPONENTS +
			"WHERE recipes.id IN (<ids>)")
	List<RecipeEntry> getEntriesForRecipes(@BindIn("ids") List<Integer> ids);

	@SqlUpdate("DELETE FROM recipe_entries WHERE recipeId = :recipeId")
	void deleteEntriesById(@Bind("recipeId") int recipieId);

	@SqlUpdate("UPDATE recipes SET name = :name, link = :link WHERE id = :id")
	void updateRecipe(@BindWithRosetta Recipe recipe);
}
