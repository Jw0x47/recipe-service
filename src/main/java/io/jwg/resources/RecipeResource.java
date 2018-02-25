package io.jwg.resources;

import com.google.inject.Inject;
import io.jwg.db.RecipeManager;
import io.jwg.models.Recipe;
import io.jwg.models.RecipeEgg;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
    @Path("/{id}")
    public Recipe getById(@PathParam("id") int id) {
        return recipeManager.getById(id);
    }

    @POST
    public Recipe addRecipe(RecipeEgg egg) {
        return recipeManager.insert(egg);
    }
}
