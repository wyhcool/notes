import { ACTION_TYPE_DECREMENT, ACTION_TYPE_INCREMENT } from "./AppActionTypes"

// 纯函数
const reducer = (state, action) => {
    const { counterCaption } = action
    switch(action.type) {
        case ACTION_TYPE_INCREMENT:
            return {
                ...state,
                [counterCaption]: state[counterCaption] + 1
            }
        case ACTION_TYPE_DECREMENT:
            return {
                ...state,
                [counterCaption]: state[counterCaption] - 1
            }
        default:
            return state
    }
}

export default reducer