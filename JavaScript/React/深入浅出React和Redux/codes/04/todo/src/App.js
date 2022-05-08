import './App.less';
import React from 'react';
import {view as Todos} from './todos/';
import {view as Filter} from './filter/';

export default class App extends React.Component {
  render() {
    return (
      <div className='app'>
        <Todos />
        <Filter />
      </div>
    );
  }
}


