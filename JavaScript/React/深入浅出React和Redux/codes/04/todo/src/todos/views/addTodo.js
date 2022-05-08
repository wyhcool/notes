import React from "react";
import './addTodo.less';
import PropTypes from 'prop-types';
import { addTodo } from "../actions";
import { connect } from "react-redux";

class AddTodo extends React.Component {
    constructor() {
        super(...arguments)
        this.onSubmit = this.onSubmit.bind(this)
        this.refInput = this.refInput.bind(this)
    }

    onSubmit(event) {
        event.preventDefault();

        const inputVal = this.input.value.trim();
        if (!inputVal) {
            return
        }
        this.props.onAdd(inputVal)
        this.input.value = ''
    }

    refInput(node) {
        this.input = node;
    }

    render() {
        return (
            <div className="add-todo">
                <form className="add-todo-wrapper" onSubmit={this.onSubmit}>
                    <input className="add-input" ref={this.refInput}></input>
                    <button className="add-btn" type="submit">添加</button>
                </form>
            </div>
        )
    }
}

AddTodo.propTypes = {
    onAdd: PropTypes.func.isRequired
};
  
  
const mapDispatchToProps = (dispatch) => {
    return {
      onAdd: (text) => {
        dispatch(addTodo(text));
      }
    }
};
  
export default connect(null, mapDispatchToProps)(AddTodo);