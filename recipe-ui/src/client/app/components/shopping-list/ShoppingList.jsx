import React from 'react';
import Reflux from "reflux";
import RecipeStore from "../../stores/RecipeStore"
import RecipeActions from "../../actions/RecipeActions"
import ListGroup from 'react-bootstrap/lib/ListGroup'
import ListGroupItem from 'react-bootstrap/lib/ListGroupItem'
import {Link} from "react-router-dom"
import Select from 'react-select'
import ShoppingListActions from '../../actions/ShoppingListActions'

class ShoppingList extends Reflux.Component {
  constructor(props) {
    super(props);
    this.store = RecipeStore;
  }

  componentDidMount() {
    RecipeActions.fetchRecipes();
  }

  static handleSelectChange(value){
    ShoppingListActions.selectRecipe(value);
  }

  render () {
    return <Select
    closeOnSelect={true}
    multi={true}
    onChange={ShoppingList.handleSelectChange}
    options={this.state.recipes.map((recipe) => {
      return {label: recipe.name, value: recipe.id}
    })}
    placeholder="Select your recipes"
    removeSelected={true}
    simpleValue
    value=""
  />

  }

}

module.exports = ShoppingList;
