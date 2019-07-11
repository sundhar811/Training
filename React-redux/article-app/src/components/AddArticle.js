import React, { Component } from "react";
import { connect } from "react-redux";

import { addArticle } from "../actions/actions";

class AddArticle extends Component {
  constructor(props) {
    super(props);

    this.state = {
      title: "",
      content: ""
    };
  }

  handleTitleChange = event => {
    this.setState({
      title: event.target.value
    });
  };

  handleContentChange = event => {
    this.setState({
      content: event.target.value
    });
  };
  render() {
    return (
      <div>
        <h1>Add an article</h1>
        <div className="form-group">
          <label className="form-label" htmlFor="title">
            Title
          </label>
          <input
            className="form-input"
            type="text"
            id="title"
            placeholder="Title"
            value={this.state.title}
            onChange={this.handleTitleChange}
          />
        </div>
        <div className="form-group">
          <label className="form-label" htmlFor="content">
            Content
          </label>
          <textarea
            className="form-input"
            id="content"
            placeholder="Your Contents"
            rows="3"
            value={this.state.content}
            onChange={this.handleContentChange}
          />
        </div>
        <button
          className="btn btn-primary"
          onClick={() => {
            if (this.state.title === "") {
              alert("Title should not be empty");
            } else if (this.state.content === "") {
              alert("Content should not be empty");
            } else {
              this.props.addArticle(this.state);
              this.props.history.push("/");
            }
          }}
        >
          Add
        </button>
      </div>
    );
  }
}

const mapDispatchToProps = dispatch => {
  return {
    addArticle: article => dispatch(addArticle(article))
  };
};

export default connect(
  null,
  mapDispatchToProps
)(AddArticle);
