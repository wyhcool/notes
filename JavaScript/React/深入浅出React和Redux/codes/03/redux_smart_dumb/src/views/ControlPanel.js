import React from 'react';
import { CounterContainer } from './Counter';
import { SummaryContainer } from './Summary';

export class ControlPanel extends React.Component {
    render() {
      return (
        <div>
           <CounterContainer name="First"></CounterContainer>
           <CounterContainer name="Second"></CounterContainer>
           <CounterContainer name="Third"></CounterContainer>
           <hr />
           <SummaryContainer></SummaryContainer>
        </div>
      );
    }
  }