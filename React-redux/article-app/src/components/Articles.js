import React from "react";
import { connect } from "react-redux";
import axios from "axios";

import Article from "./Article";
import { fetchArticles } from "../actions/actions";

function Articles(props) {
  const { data } = props;
  const articles = data.map(article => (
    <Article title={article.title} key={article.title} prop={props}>
      {article.content}
    </Article>
  ));
  return (
    <div className="content">
      {articles}
      <button className="btn btn-primary" onClick={props.fetchArticles}>
        More
      </button>
    </div>
  );
}

const mapStateToProps = state => {
  return {
    data: state.data
  };
};

const mapDispatchToProps = dispatch => {
  return {
    fetchArticles: () => {
      axios.get("http://localhost:3001/articles").then(response => {
        dispatch(fetchArticles(response.data));
      }).catch(console.log("Connection unavailable: Can't fetch data"));
    }
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Articles);
