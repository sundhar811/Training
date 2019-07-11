import React, { Component } from 'react';
import '../App.css';
import Display from './Display';
import Calc from './Calc';
import calculate from '../logic/calculate'

class App extends Component {
  constructor(props) {
    super(props);

    this.state = {
      operand1: 0,
      operand2: 0,
      operator: "+",
      total: 0
    };
  }

  clearAll = ()=>{
    this.setState({
      operand1: 0,
      operand2: 0,
      operator: "+",
      total: 0
    })
  }

  updateTotal = (obj)=>{
    this.setState({
      total: calculate(obj)
    })
  }

  handleClick = buttonName => {
    this.setState({
      operator: buttonName
    }, ()=>{
      this.updateTotal(this.state)
    });
  };

  handleOp1Update = obj => {
    this.setState(obj, () => {
      this.updateTotal(this.state);
    });
  };

  handleOp2Update = obj => {
    this.setState(obj, () => {
      this.updateTotal(this.state);
    });
  };

  render() {
    return (
      <div className="App">
        <div className="leftPane">
          <Calc
            operand1={this.state.operand1}
            operand2={this.state.operand2}
            operator={this.state.operator}
            clickHandler={this.handleClick}
            updateOp1={this.handleOp1Update}
            updateOp2={this.handleOp2Update}
            clear={this.clearAll}
          />
        </div>
        <div className="rightPane">
          <Display
            operand1={this.state.operand1}
            operand2={this.state.operand2}
            operator={this.state.operator}
            total={this.state.total}
          />
        </div>
      </div>
    );
  }
}

export default App;
