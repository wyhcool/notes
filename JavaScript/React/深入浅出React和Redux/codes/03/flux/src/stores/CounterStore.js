import {EventEmitter} from "events";
import { ACTION_TYPE_DECREMENT, ACTION_TYPE_INCREMENT } from "../AppActionTypes";
import AppDispatcher from "../AppDispatcher";

const counterValues = {
    'First': 0,
    'Second': 10,
    'Third': 30
}

const CHANGE_EVENT = 'change_counter'

const CounterStore = Object.assign({}, EventEmitter.prototype, {
    getCounterValues: function() {
        return counterValues;
    },
    emitChange: function() {
        this.emit(CHANGE_EVENT)
    },
    addChangeListener: function(callback) {
        this.on(CHANGE_EVENT, callback)
    },
    removeChangeListener:  function(callback) {
        this.removeListener(CHANGE_EVENT, callback)
    },
    dispatchToken: () => {}
})

CounterStore.dispatchToken = AppDispatcher.register((action) => {
    if (action.type === ACTION_TYPE_DECREMENT) {
        counterValues[action.counterCaption]--;
        CounterStore.emitChange()
    } else if (action.type === ACTION_TYPE_INCREMENT) {
        counterValues[action.counterCaption]++;
        CounterStore.emitChange()
    }
})

export default CounterStore;