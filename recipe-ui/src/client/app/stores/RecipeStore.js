import Reflux from "reflux";
import RecipeActions from "../actions/RecipeActions"
import {BASE_API_URL} from "../ApiConstants"
import ResourceProvider from "../services/ResourceProvider"

class RecipeStore extends Reflux.Store {
  constructor() {
    super();
    this.state = {
      "name": "",
      "link": "",
      "id": 0,
      "recipes": []
    };
    this.listenTo(RecipeActions.selectRecipe, this.fetchRecipe);
    this.listenTo(RecipeActions.fetchRecipes, this.fetchRecipes);
  }

  fetchRecipes() {
    console.log("Starting to load recipe list");
    const promise = new ResourceProvider({
      url: `${BASE_API_URL}/recipes`,
      type: 'GET'
    }).send();

    const store = this;
    return promise.done(function (data) {
      const newState = Object.assign(store.state, {recipes: data});
      store.setState(newState)
    });
  }

  fetchRecipe (recipeId) {
    console.log("Starting to load recipe: " + recipeId);
    const promise = new ResourceProvider({
      url: `${BASE_API_URL}/recipes/${recipeId}`,
      type: 'GET'
    }).send();

    const store = this;
    return promise.done(function (data) {
      const newData = Object.assign(store.state, data);
      store.setState(newData)
    });
  }
}

export default RecipeStore;