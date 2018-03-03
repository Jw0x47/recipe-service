import React from 'react';
import Recipe from "./Recipe.jsx";
import Breadcrumb from 'react-bootstrap/lib/Breadcrumb';
import Reflux from "reflux";
import RecipeViewerStore from "./stores/RecipeViewerStore.jsx"



class RecipeViewer extends Reflux.Component {

  constructor(props) {
    super(props);
    this.state = {};
    this.store = RecipeViewerStore;
  }

  render() {
    return (
      <div>
        <Breadcrumb>
          <Breadcrumb.Item href="#">{this.state.collection}</Breadcrumb.Item>
          <Breadcrumb.Item active>{this.state.recipe}</Breadcrumb.Item>
        </Breadcrumb>
        <Recipe/>
      </div>
    );
  }

}

export default RecipeViewer;
