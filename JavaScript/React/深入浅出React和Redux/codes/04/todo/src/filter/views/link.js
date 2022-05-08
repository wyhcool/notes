import React from "react";
import { connect } from "react-redux";
import PropTypes from 'prop-types';
import { setFilter } from "../actions";

class Link extends React.Component {
    render() {
        const {active, children, onClick} = this.props;
        return (
            <div className="filter-item" onClick={onClick}>
                <input type="radio" checked={active} readOnly></input>
                {children}
            </div>
        )
    }
}


Link.propTypes = {
    active: PropTypes.bool.isRequired,
    children: PropTypes.node.isRequired,
    onClick: PropTypes.func.isRequired
};
  
const mapStateToProps = (state, ownProps) => {
    return {
        active: state.filter === ownProps.filter
    }
};
  
const mapDispatchToProps = (dispatch, ownProps) => ({
    onClick: () => {
        dispatch(setFilter(ownProps.filter));
    }
});
  
export default connect(mapStateToProps, mapDispatchToProps)(Link);
