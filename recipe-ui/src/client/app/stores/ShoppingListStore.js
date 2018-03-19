import Reflux from "reflux";
import RecipeActions from "../actions/RecipeActions"
import ShoppingListActions from "../actions/ShoppingListActions"
import {BASE_API_URL} from "../ApiConstants"
import ResourceProvider from "../services/ResourceProvider"

class ShoppingListStore extends Reflux.Store {
  constructor() {
    super();
    this.state = {
      "recipes": [],
      "selectedRecipes": []

    };
    this.listenTo(RecipeActions.fetchRecipes, this.fetchRecipes);
    this.listenTo(ShoppingListActions.selectRecipe, this.selectRecipe)
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

  selectRecipe (selectedRecipeId) {
    selectedRecipe = this.state.recipes.find(recipe => recipe.id === selectedRecipeId);

    store.setState({
      selectedRecipes: store.state.selectedRecipes.concat([selectedRecipe]),
      recipes: store.state.recipes
    });
  }
}

module.exports = ShoppingListStore;