import React from "react";
import { NavLink } from "react-router-dom";

function NavBar(props) {
  return (
    <header className="navbar">
      <section className="navbar-section">
        <NavLink to="/">
          <button className="btn btn-link">Article App</button>
        </NavLink>
      </section>
      <section className="navbar-section">
        <NavLink to="/">
          <button className="btn btn-link">Home</button>
        </NavLink>
        <NavLink to="/add">
          <button className="btn btn-link">Add Article</button>
        </NavLink>
      </section>
    </header>
  );
}

export default NavBar;
