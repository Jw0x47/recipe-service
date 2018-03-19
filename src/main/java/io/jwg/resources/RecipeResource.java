package io.jwg.resources;

import com.google.common.base.Joiner;
import com.google.inject.Inject;
import io.jwg.db.RecipeManager;
import io.jwg.models.Recipe;
import io.jwg.models.RecipeEggWithEntries;
import io.jwg.models.RecipeEntry;
import io.jwg.models.RecipeWithEntries;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/recipes")
public class RecipeResource {

	private RecipeManager recipeManager;

	@Inject
	public RecipeResource(RecipeManager recipeManager) {
		this.recipeManager = recipeManager;
	}

	@GET
	public List<Recipe> listRecipes() {
		return recipeManager.listRecipes();
	}

	@GET
	@Path("/{id}")
	public RecipeWithEntries getById(@PathParam("id") int id) {
		return recipeManager.getByIdWithEntries(id);
	}

	@PUT
	@Path("/{recipeId}")
	public RecipeWithEntries updateById(@PathParam("recipeId") int recipeId, RecipeEggWithEntries eggWithEntries) {
		return recipeManager.update(recipeId, eggWithEntries);
	}

	@POST
	@Path("/add")
	public Recipe addRecipe(RecipeEggWithEntries egg) {
		return recipeManager.insert(egg);
	}


	@GET
	@Path("/entries")
	public List<RecipeEntry> getEntriesForRecipes(@QueryParam("recipeId") List<Integer> ids) {
		return recipeManager.getEntriesForRecipes(ids);
	}

	@GET
	@Path("/shopping-list")
	public List<String> getShoppingList(@QueryParam("recipeId") List<Integer> ids, @QueryParam("pretty") Optional<Boolean> pretty) {
		return recipeManager.getShoppingList(ids);
	}

	@GET
	@Path("/shopping-list/text")
	@Produces(MediaType.TEXT_PLAIN)
	public String getShoppingList(@QueryParam("recipeId") List<Integer> ids) {
		return Joiner.on("\n").join(recipeManager.getShoppingList(ids));
	}

}
