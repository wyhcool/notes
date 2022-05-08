import { combineReducers, createStore } from "redux";
import { reducer as todosReducer } from './todos'
import { reducer as filterReducer } from "./filter";

const reducer =  combineReducers({
    todos: todosReducer,
    filter: filterReducer
})

const storeEnhancers = null

// export default createStore(reducer, {}, storeEnhancers)
export default createStore(reducer, {})