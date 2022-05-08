import React from 'react';
import CounterStore from '../stores/CounterStore';
import { actionDecrement, actionIncrement } from '../AppActions';

export class Counter extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            count: CounterStore.getCounterValues()[props.name]
        };
        this.onChange = this.onChange.bind(this)
    }

    componentDidMount() {
        CounterStore.addChangeListener(this.onChange)
    }

    componentWillUnmount() {
        CounterStore.removeChangeListener(this.onChange)
    }

    onIncrementBtnClick() {
        actionIncrement(this.props.name)
    }

    onDecrementBtnClick() {
        actionDecrement(this.props.name)
    }

    onChange() {
        const newCount = CounterStore.getCounterValues()[this.props.name]
        this.setState({
            count: newCount
        })
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

 