import React from 'react';
import store from '../Store';

function Summary({summary}) {
    return (
        <div>
            <div>Summary: { summary } </div>
        </div>
    )
}

export class SummaryContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = this.getOwnState()
        this.onChange = this.onChange.bind(this)
    }

    getOwnState() {
        const state = store.getState();
        let summary = 0
        for (const key in state) {
            if (state.hasOwnProperty(key)) {
                summary += state[key]
            }
        }
        return {summary}
    }

    componentDidMount() {
        store.subscribe(this.onChange)
    }

    componentWillUnmount() {
        // store.unsubscribe(this.onChange)
    }

    onChange() {
        this.setState(this.getOwnState());
    }

    render() {
        return (
            <Summary summary={this.state.summary}></Summary>
      );
    }
  }

 