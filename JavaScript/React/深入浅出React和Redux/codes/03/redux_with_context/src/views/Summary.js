import React from 'react';
import PropTypes from 'prop-types'

function Summary({summary}) {
    return (
        <div>
            <div>Summary: { summary } </div>
        </div>
    )
}

export class SummaryContainer extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = this.getOwnState()
        this.onChange = this.onChange.bind(this)
    }

    getOwnState() {
        const state = this.context.store.getState();
        let summary = 0
        for (const key in state) {
            if (state.hasOwnProperty(key)) {
                summary += state[key]
            }
        }
        return {summary}
    }

    componentDidMount() {
        this.context.store.subscribe(this.onChange)
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

  SummaryContainer.contextTypes = {
    store: PropTypes.object
  }
 