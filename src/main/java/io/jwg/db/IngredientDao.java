package io.jwg.db;

import io.jwg.models.Measurement;
import io.jwg.models.RecipeComponent;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface IngredientDao {


	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO components (name) values (:name)")
	int createComponent(@Bind("name") String name);

	@SqlUpdate("INSERT INTO recipe_entries (recipeId, ingredientId, amount, measurement) " +
			"VALUES (:recipeId, :ingredientId, :amount, :measurement)")
	void createRecipeEntry(@Bind("recipeId") int recipeId,
												 @Bind("ingredientId") int ingredientId,
												 @Bind("amount") double amount,
												 @Bind("measurement") Measurement measurement);

	@SqlQuery("SELECT * FROM components where name = :name")
	RecipeComponent getComponentByName(@Bind("name") String name);

}
