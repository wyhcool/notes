import React from "react";
import TodoItem from "./todoItem";
import './todoList.less'
import PropTypes from 'prop-types'
import { connect } from "react-redux";
import { removeTodo, toggleTodo } from "../actions";
import { FilterTypes } from "../../constant";

class TodoList extends React.Component {
    render() {
        const {todos, onToggleTodo, onRemoveTodo} = this.props;
        return (
            <ul className="todo-list">
                {
                    todos.map(item => (
                        <TodoItem
                            key={item.id}
                            text={item.text}
                            completed={item.completed}
                            onToggle={() => onToggleTodo(item.id)}
                            onRemove={() => onRemoveTodo(item.id)}
                        />)
                    )
                }
            </ul>
        )
    }
}

TodoList.propTypes = {
    todos: PropTypes.array.isRequired
};


const selectVisibleTodos = (todos, filter) => {
    switch (filter) {
      case FilterTypes.ALL:
        return todos;
      case FilterTypes.COMPLETED:
        return todos.filter(item => item.completed);
      case FilterTypes.UNCOMPLETED:
        return todos.filter(item => !item.completed);
      default:
        throw new Error('unsupported filter');
    }
}

const mapStateToProps = (state) => {
    return {
      todos: selectVisibleTodos(state.todos, state.filter)
    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onToggleTodo: (id) => {
            dispatch(toggleTodo(id));
        },
        onRemoveTodo: (id) => {
            dispatch(removeTodo(id));
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(TodoList);