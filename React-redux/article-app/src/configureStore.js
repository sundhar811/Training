import { createStore, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import reducer from "./store/reducer";

const addLoggingToDispatch = store => {
  const rawDispatch = store.dispatch;
  if (!console.group) {
    return rawDispatch;
  }

  return action => {
    console.group(action.type);
    console.log("%c prev state", "color: gray", store.getState());
    console.log("%c action", "color: blue", action);
    const returnValue = rawDispatch(action);
    console.log("%c next state", "color: green", store.getState());
    console.groupEnd(action.type);
    return returnValue;
  };
};

const error = store => next => action => {
  try {
    next(action);
  } catch (e) {
    console.log("Error: ", e);
  }
};

const middleware = applyMiddleware(error, thunk);

const configureStore = () => {
  const store = createStore(reducer, middleware);

  if (process.env.NODE_ENV !== "production") {
    store.dispatch = addLoggingToDispatch(store);
  }

  return store;
};

export default configureStore;
