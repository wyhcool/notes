import React from 'react';
import { actionDecrement, actionIncrement } from '../AppActions';
import store from '../Store';

export class Counter extends React.Component {
    constructor(props) {
        super(props);
        this.state = this.getOwnState()
        this.onChange = this.onChange.bind(this)
    }

    getOwnState() {
        return {
            count: store.getState()[this.props.name]
        }
    }

    componentDidMount() {
        store.subscribe(this.onChange)
    }

    componentWillUnmount() {
        // store.unsubscribe(this.onChange)
    }

    onIncrementBtnClick() {
        store.dispatch(actionIncrement(this.props.name))
    }

    onDecrementBtnClick() {
        store.dispatch(actionDecrement(this.props.name))
    }

    onChange() {
       this.setState(this.getOwnState())
    }

    render() {
        return (
        <div>
            <div>Hello {this.props.name}</div>
            <div>
                <button onClick={() => this.onIncrementBtnClick()}>加1</button>
                <button onClick={() => this.onDecrementBtnClick()}>减1</button>
            </div>
            <div>Count: { this.state.count} </div>
            <hr />
        </div>
      );
    }
  }

 