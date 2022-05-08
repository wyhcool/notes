import { ACTION_TYPE_DECREMENT, ACTION_TYPE_INCREMENT } from "./AppActionTypes"
import AppDispatcher from "./AppDispatcher"

export const actionIncrement = (counterCaption) => {
    AppDispatcher.dispatch({
        type: ACTION_TYPE_INCREMENT,
        counterCaption
    })
}

export const actionDecrement = (counterCaption) => {
    AppDispatcher.dispatch({
        type: ACTION_TYPE_DECREMENT,
        counterCaption
    })
}