import { data } from "./initialData";

import { getArticle, getArticleIndex } from "../store/getArticleIndex";

const initialState = {
  can_fetch_more: true,
  current_edit: -1,
  data
};

const reducer = (state = initialState, action) => {
  const newState = { ...state };

  if (action.type === "ADD_ARTICLE") {
    newState.data.push(action.article);
    return {
      ...state,
      data: newState.data
    };
  }

  if (action.type === "GO_TO_EDIT") {
    return {
      ...state,
      current_edit: getArticle(newState.data, action.title).concat(
        getArticleIndex(newState.data, action.title)
      )
    };
  }

  if (action.type === "EDIT_ARTICLE") {
    newState.data[action.index] = action.article;
    return {
      ...state,
      data: newState.data,
      current_edit: []
    };
  }

  if (action.type === "DELETE_ARTICLE") {
    let index = getArticleIndex(newState.data, action.title);
    let newData = newState.data
      .slice(0, index)
      .concat(newState.data.slice(index + 1));
    return {
      ...state,
      data: newData
    };
  }

  if (action.type === "FETCH_ARTICLES") {
    if (newState.can_fetch_more) {
      return {
        ...state,
        data: [...newState.data, ...action.articles],
        can_fetch_more: !newState.can_fetch_more
      };
    }
  }

  return newState;
};

export default reducer;
