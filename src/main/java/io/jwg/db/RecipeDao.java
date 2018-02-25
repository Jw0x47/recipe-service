package io.jwg.db;

import com.hubspot.rosetta.jdbi.BindWithRosetta;
import io.jwg.models.Recipe;
import io.jwg.models.RecipeEgg;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface RecipeDao {

	@GetGeneratedKeys
	@SqlUpdate("INSERT INTO recipes (name, link) values (:name, :link)")
	int insertRecipe(@BindWithRosetta RecipeEgg recipe);

	@SqlQuery("SELECT * FROM recipes where id = :id")
	Recipe getById(@Bind("id") int id);
}
