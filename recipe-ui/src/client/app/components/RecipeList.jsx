import React from 'react';
import Reflux from "reflux";
import RecipeStore from "../stores/RecipeStore"
import RecipeActions from "../actions/RecipeActions"
import ListGroup from 'react-bootstrap/lib/ListGroup'
import ListGroupItem from 'react-bootstrap/lib/ListGroupItem'
import {Link} from "react-router-dom";

class RecipeList extends Reflux.Component {
  constructor(props) {
    super(props);
    this.store = RecipeStore;
  }

  componentDidMount() {
    RecipeActions.fetchRecipes();
  }


  render() {
    return <ListGroup>
      {buildItems(this.state.recipes)}
    </ListGroup>
  }


}

function buildItems(recipes) {
    return recipes.map((recipe) => {
      return <ListGroupItem key={JSON.stringify(recipe)}>
        <Link to={"/" + recipe.id}>
          {recipe.name}
        </Link>
      </ListGroupItem>
    })
  }


module.exports = RecipeList;