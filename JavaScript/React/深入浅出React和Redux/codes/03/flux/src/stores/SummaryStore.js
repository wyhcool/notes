import {EventEmitter} from "events";
import { ACTION_TYPE_DECREMENT, ACTION_TYPE_INCREMENT } from "../AppActionTypes";
import AppDispatcher from "../AppDispatcher";
import CounterStore from "./CounterStore";

function computeSummary(counterValues) {
    let summary = 0;
    for (const key in counterValues) {
        if (counterValues.hasOwnProperty(key)) {
            summary += counterValues[key]
        }
    }
    return summary
}

const CHANGE_EVENT = 'change_summary'

const SummaryStore = Object.assign({}, EventEmitter.prototype, {
    getSummary: function() {
        return computeSummary(CounterStore.getCounterValues());
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

SummaryStore.dispatchToken = AppDispatcher.register((action) => {
    if (action.type === ACTION_TYPE_DECREMENT || action.type === ACTION_TYPE_INCREMENT) {
        AppDispatcher.waitFor([CounterStore.dispatchToken])
        SummaryStore.emitChange()
    }
})

export default SummaryStore;