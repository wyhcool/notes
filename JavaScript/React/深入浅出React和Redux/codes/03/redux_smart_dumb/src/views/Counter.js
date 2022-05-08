import React from 'react';
import PropTypes from 'prop-types';
import store from '../Store';
import { actionDecrement, actionIncrement } from '../AppActions';

class Counter extends React.Component {
    render() {
        const { name, onIncrement, onDecrement, count} = this.props;
        return (
        <div>
            <div>Hello {name}</div>
            <div>
                <button onClick={onIncrement}>加1</button>
                <button onClick={onDecrement}>减1</button>
            </div>
            <div>Count: {count} </div>
            <hr />
        </div>
      );
    }
  }

Counter.propTypes = {
    name: PropTypes.string.isRequired,
    onIncrement: PropTypes.func.isRequired,
    onDecrement: PropTypes.func.isRequired,
    count: PropTypes.number.isRequired
  };

export class CounterContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = this.getOwnState()
        this.onChange = this.onChange.bind(this)
        this.onIncrementBtnClick = this.onIncrementBtnClick.bind(this)
        this.onDecrementBtnClick = this.onDecrementBtnClick.bind(this)
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
            <Counter 
                name={this.props.name}
                onIncrement={this.onIncrementBtnClick}
                onDecrement={this.onDecrementBtnClick}
                count={this.state.count}
            ></Counter>
        )
    }
}

CounterContainer.propTypes = {
    name: PropTypes.string.isRequired
  };
 