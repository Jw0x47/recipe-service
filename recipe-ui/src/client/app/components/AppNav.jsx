import React from 'react';
import {Nav, Navbar, NavItem} from 'react-bootstrap/lib'
import {Link} from "react-router-dom"

class AppNav extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return <Navbar>
      <Navbar.Header>
        <Link to="/">
          <Navbar.Brand>Recipe Manager</Navbar.Brand>
        </Link>
      </Navbar.Header>
      <Nav>
        <NavItem href="/shopping-list">Shopping List</NavItem>
      </Nav>
    </Navbar>
  }
}

module.exports = AppNav;
