import React from 'react';
import { Counter } from './Counter';
import { Summary } from './Summary';

export class ControlPanel extends React.Component {
    render() {
      return (
        <div>
           <Counter name="First"></Counter>
           <Counter name="Second"></Counter>
           <Counter name="Third"></Counter>
           <hr />
           <Summary></Summary>
        </div>
      );
    }
  }