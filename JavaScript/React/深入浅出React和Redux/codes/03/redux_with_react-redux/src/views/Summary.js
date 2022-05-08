import React from 'react';
import { connect } from 'react-redux';

function Summary({summary}) {
    return (
        <div>
            <div>Summary: { summary } </div>
        </div>
    )
}


function mapStateToProps(state, ownProps) {
    let summary = 0
    for (const key in state) {
        if (state.hasOwnProperty(key)) {
            summary += state[key]
        }
    }
    return {
        summary
    }
}

  
export default connect(mapStateToProps)(Summary);
