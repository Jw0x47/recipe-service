import Reflux from "reflux";
import RecipeViewerActions from "../actions/RecipeViewerActions.es6"
import {BASE_API_URL} from "../ApiConstants.es6"
import ResourceProvider from "../services/ResourceProvider.es6"

// TODO explore actions
// var Actions = Reflux.createActions(['firstAction', 'secondAction']);
var statusUpdate = Reflux.createAction();

var RecipeViewerStore = Reflux.createStore({
  listenables: [RecipeViewerActions],
  recipe: {},
  init: function () {
    this.fetchRecipe(1);
  },

  fetchRecipe: function (recipeId) {
    const recipePromise = new ResourceProvider({
      url: `${BASE_API_URL}/recipe/${recipeId}`,
      type: 'GET'
    }).send();

    return recipePromise.done(function (data) {
      this.recipe = data;
    });
  }

});

export default RecipeViewerStore;