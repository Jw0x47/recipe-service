import React from 'react';
import {render} from 'react-dom';
import RecipeViewer from "./RecipeViewer.jsx";

class App extends React.Component {
  render () {
    return (
        <div>
          <RecipeViewer/>
        </div>
    );
  }
}


render(<App/>, document.getElementById('app'));
