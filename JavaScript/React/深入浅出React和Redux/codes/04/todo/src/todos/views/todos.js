import React from "react";
import AddTodo from "./addTodo";
import TodoList from "./todoList";
import './todos.less'

export default class Todos extends React.Component {
    render() {
        return (
            <div className="todos">
                <AddTodo />
                <TodoList />
            </div>
        )
    }
}