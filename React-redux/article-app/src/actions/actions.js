export const addArticle = article => {
  return {
    type: "ADD_ARTICLE",
    article
  };
};

export const goToEditPage = title => {
  return {
    type: "GO_TO_EDIT",
    title
  };
};

export const editArticle = (article, index) => {
  return {
    type: "EDIT_ARTICLE",
    article,
    index
  };
};

export const deleteArticle = title => {
  return {
    type: "DELETE_ARTICLE",
    title
  };
};

export const fetchArticles = articles => {
  return {
    type: "FETCH_ARTICLES",
    articles
  };
};
