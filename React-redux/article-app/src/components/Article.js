import React from "react";
import { connect } from "react-redux";

import { goToEditPage, deleteArticle } from "../actions/actions";

function Article(props) {
  return (
    <div className="article">
      <h2>{props.title}</h2>
      <p>{props.children}</p>
      <button
        className="btn btn-success"
        onClick={() => {
          props.dispatchToEdit(props.title);
          props.prop.history.push("/edit");
        }}
      >
        Edit
      </button>
      <button
        className="btn btn-error"
        onClick={() => props.deleteItem(props.title)}
      >
        Delete
      </button>
    </div>
  );
}

const mapDispatchToProps = dispatch => {
  return {
    dispatchToEdit: title => dispatch(goToEditPage(title)),
    deleteItem: title => dispatch(deleteArticle(title))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(Article);
