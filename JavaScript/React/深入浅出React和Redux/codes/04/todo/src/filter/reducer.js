import { SET_FILTER } from "./actionTypes";
import { FilterTypes } from "../constant";

const reducer = (state = FilterTypes.ALL, action) => {
    switch(action.type) {
        case SET_FILTER: 
            return action.filterType;
        default:
            return state
    }
}

export default reducer;