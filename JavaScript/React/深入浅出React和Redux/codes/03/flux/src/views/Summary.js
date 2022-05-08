import React from 'react';
import SummaryStore from '../stores/SummaryStore';

export class Summary extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            summary: SummaryStore.getSummary()
        };
        this.onChange = this.onChange.bind(this)
    }

    componentDidMount() {
        SummaryStore.addChangeListener(this.onChange)
    }

    componentWillUnmount() {
        SummaryStore.removeChangeListener(this.onChange)
    }

    onChange() {
        const newSummary = SummaryStore.getSummary()
        this.setState({
            summary: newSummary
        })
    }

    render() {
        return (
        <div>
            <div>Summary: { this.state.summary} </div>
        </div>
      );
    }
  }

 