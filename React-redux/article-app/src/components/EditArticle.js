import React, { Component } from "react";
import { connect } from "react-redux";

import { editArticle } from "../actions/actions";

class EditArticle extends Component {
  constructor(props) {
    super(props);

    this.state = {
      title: this.props.article[0].title,
      content: this.props.article[0].content
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
        <h1>Edit the Article</h1>
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
              this.props.editArticle(this.state, this.props.article[1]);
              this.props.history.push("/");
            }
          }}
        >
          Save
        </button>
      </div>
    );
  }
}

const mapStateToProps = state => {
  return {
    article: state.current_edit
  };
};

const mapDispatchToProps = dispatch => {
  return {
    editArticle: (article, index) => dispatch(editArticle(article, index))
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EditArticle);
