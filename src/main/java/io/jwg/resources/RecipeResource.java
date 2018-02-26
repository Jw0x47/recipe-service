package io.jwg.resources;

import com.google.inject.Inject;
import io.jwg.db.RecipeManager;
import io.jwg.models.Recipe;
import io.jwg.models.RecipeEggWithEntries;
import io.jwg.models.RecipeEntry;
import io.jwg.models.RecipeWithEntries;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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
	public List<RecipeEntry> getEntriesForRecipes(@QueryParam("id") List<Integer> ids) {
		return recipeManager.getEntriesForRecipes(ids);
	}
}
