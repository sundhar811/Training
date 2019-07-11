// json-server db.json --port 3001

import React from "react";
import ReactDOM from "react-dom";

import "spectre.css";
import "./index.css";

import Root from "./components/Root";

import configureStore from "./configureStore";

const store = configureStore();

ReactDOM.render(<Root store={store} />, document.getElementById("root"));
