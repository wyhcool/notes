import React from "react";
import { FilterTypes } from "../../constant";
import Link from "./link";

export default class Filter extends React.Component {
    render() {
        return (
            <div className="filter">
                <Link filter={FilterTypes.ALL}> {FilterTypes.ALL} </Link>
                <Link filter={FilterTypes.COMPLETED}> {FilterTypes.COMPLETED} </Link>
                <Link filter={FilterTypes.UNCOMPLETED}> {FilterTypes.UNCOMPLETED} </Link>
            </div>
        )
    }
}