import React, { Component } from "react";
import { BrowserRouter as Router } from "react-router-dom";
import Route from "react-router-dom/Route";

import NavBar from "./NavBar";
import EditArticle from "./EditArticle";
import Articles from "./Articles";
import AddArticle from "./AddArticle";

class App extends Component {
  render() {
    return (
      <Router>
        <div>
          <NavBar />
          <Route path="/" exact strict component={Articles} />
          <Route path="/add" exact strict component={AddArticle} />
          <Route path="/edit" exact strict component={EditArticle} />
        </div>
      </Router>
    );
  }
}

export default App;
