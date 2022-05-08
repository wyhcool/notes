import React from "react";
import './todoItem.less'
import PropTypes from 'prop-types'

class TodoItem extends React.Component {
    render() {
        const {onToggle, onRemove, completed, text} = this.props
        return (
            <li className="todo-item">
               <input className="toggle-check" type="checkbox" checked={completed ? 'checked' : ''} readOnly onClick={onToggle}></input>
                <label className="text">{text}</label>
                <button className="remove-btn" onClick={onRemove}>X</button>
            </li>
        )
    }
}

TodoItem.propTypes = {
    onToggle: PropTypes.func.isRequired,
    onRemove: PropTypes.func.isRequired,
    completed: PropTypes.bool.isRequired,
    text: PropTypes.string.isRequired
  }
  
  export default TodoItem;