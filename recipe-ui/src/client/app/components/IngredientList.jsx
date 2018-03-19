import React from 'react';
import Reflux from "reflux";
import Ingredient from "./Ingredient"
import RecipeStore from "../stores/RecipeStore";

class IngredientList extends Reflux.Component {
  constructor(props) {
    super(props);
    this.store = RecipeStore;
    this.state = {entries: []}; // initial state
  }

  render() {
    return <ul>
      {this.state.entries.map((entry) => {
        return <Ingredient key={JSON.stringify(entry)} entry={entry} />
      })}
    </ul>
  }

}

module.exports = IngredientList;