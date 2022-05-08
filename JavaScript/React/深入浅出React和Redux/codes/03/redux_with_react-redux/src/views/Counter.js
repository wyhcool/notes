import React from 'react';
import PropTypes from 'prop-types';
import { actionDecrement, actionIncrement } from '../AppActions';
import { connect } from 'react-redux';

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

function mapStateToProps(state, ownProps) {
    return {
        count: state[ownProps.name]
    }
}

function mapDispatchToProps(dispatch, ownProps) {
    return {
        onIncrement: () => {
            dispatch(actionIncrement(ownProps.name));
        },
        onDecrement: () => {
            dispatch(actionDecrement(ownProps.name));
        }
    }
}
  
export default connect(mapStateToProps, mapDispatchToProps)(Counter);