import React from 'react';
import {render} from 'react-dom';
import createBrowserHistory from 'history/createBrowserHistory'
import {Route, Router, Switch} from "react-router-dom";
import Grid from 'react-bootstrap/lib/Grid'
import Col from 'react-bootstrap/lib/Col'
import Recipe from "./components/Recipe";
import AppNav from './components/AppNav'
import RecipeList from './components/RecipeList'
import ShoppingList from './components/shopping-list/ShoppingList'

const routes = (
  <Router history={createBrowserHistory()}>
    <Grid>
      <Col style={{"marginTop": "15px"}}>
        <AppNav/>
        <Switch>
          <Route exact path="/" name="app" component={RecipeList}/>
          <Route name="shopping-list" path="/shopping-list" component={ShoppingList}/>
          <Route name="recipe" path="/:id" render={(match) => <Recipe id={match}/>}/>
        </Switch>
      </Col>
    </Grid>
  </Router>
);


render(routes, document.getElementById('app'));
