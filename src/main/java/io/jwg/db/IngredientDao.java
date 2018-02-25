package io.jwg.db;

import com.hubspot.rosetta.jdbi.BindWithRosetta;
import io.jwg.models.Ingredient;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import java.util.List;

public interface IngredientDao {

	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO ingredients (name, amount, measurement, recipeId) values (:name, :amount, :measurement, :recipeId)")
	int insertIngredient(@BindWithRosetta Ingredient ingredient);

	@SqlQuery("SELECT * FROM ingredients where recipeId = :id")
	List<Ingredient> getIngredientsByRecipeId(@Bind("id") int id);
}
