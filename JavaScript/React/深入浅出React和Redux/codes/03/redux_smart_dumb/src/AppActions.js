import { ACTION_TYPE_DECREMENT, ACTION_TYPE_INCREMENT } from "./AppActionTypes"

export const actionIncrement = (counterCaption) => {
    return {
        type: ACTION_TYPE_INCREMENT,
        counterCaption
    }
}

export const actionDecrement = (counterCaption) => {
   return {
        type: ACTION_TYPE_DECREMENT,
        counterCaption
    }
}