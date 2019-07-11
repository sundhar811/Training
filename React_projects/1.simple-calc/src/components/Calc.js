import React, { Component } from 'react'
import ButtonPanel from './ButtonPanel';
import InputPanel from './InputPanel';

class Calc extends Component {
  handleClick = buttonName => {
    this.props.clickHandler(buttonName);
  };

  handleOp1Update = obj => {
    this.props.updateOp1(obj);
  };

  handleOp2Update = obj => {
    this.props.updateOp2(obj);
  };

  render() {
    return (
      <div className="calc">
        <InputPanel
          operand1={this.props.operand1}
          operand2={this.props.operand2}
          operator={this.props.operator}
          updateOp1={this.handleOp1Update}
          updateOp2={this.handleOp2Update}
        />
        <ButtonPanel 
          clickHandler={this.handleClick} 
          clear={this.props.clear}
        />
      </div>
    );
  }
}

export default Calc
