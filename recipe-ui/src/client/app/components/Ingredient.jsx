import React from 'react';
import Reflux from "reflux";

class Ingredient extends Reflux.Component {
  constructor(props) {
    super(props);
    this.state = this.props.entry;

  }

  render() {
    return <li>
      {this.state.amount} {this.state.measurementFormatted} {this.state.name}
    </li>
  }
}

module.exports = Ingredient;