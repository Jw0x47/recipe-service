import React from 'react';
import Panel from 'react-bootstrap/lib/Panel';


class Recipe extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      name: "Test Recipe",
      link: "http://www.google.com",
    };
  }

  render() {
    return (
      <div>
        <Panel>

          <Panel.Heading>
            <a href={this.state.link}>
              <Panel.Title componentClass="h3">{this.state.name}</Panel.Title>
            </a>
          </Panel.Heading>
          <Panel.Body>
            Ingredients go here
          </Panel.Body>
        </Panel>
      </div>
    );
  }

}

export default Recipe;
