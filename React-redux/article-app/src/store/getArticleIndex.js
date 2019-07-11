export const getArticle = (data, title) => {
  return data.filter(element => element.title === title);
};

export const getArticleIndex = (data, title) => {
  let i;
  data.forEach((element, index) => {
    if (element.title === title) {
      i = index;
    }
  });
  return i;
};
