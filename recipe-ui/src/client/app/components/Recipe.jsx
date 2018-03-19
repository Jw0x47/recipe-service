import React from 'react';
import Breadcrumb from 'react-bootstrap/lib/Breadcrumb';
import Reflux from "reflux";
import RecipeStore from "../stores/RecipeStore"
import RecipeVeiwerActions from "../actions/RecipeActions"
import IngredientList from "./IngredientList"
import Panel from 'react-bootstrap/lib/Panel'

class Recipe extends Reflux.Component {
  constructor(props) {
    super(props);
    this.store = RecipeStore;
  }

  componentDidMount() {
    RecipeVeiwerActions.selectRecipe(this.props.id.match.params.id);
  }

  render() {
    return <div>
      <Panel>
        {buildHeading(this.state.name, this.state.link)}
        <Panel.Body>
          <IngredientList entries={this.state.entries}/>
        </Panel.Body>
      </Panel>
    </div>
  }
}

function buildHeading(name, link) {
  return <Panel.Heading>
    <a href={link}>
      <Panel.Title componentClass="h3">
        {name}
      </Panel.Title>
    </a>
  </Panel.Heading>
}

module.exports = Recipe;
