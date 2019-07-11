import React, { Component } from 'react'

class InputPanel extends Component {
  handleOp1Update = event => {
    this.props.updateOp1({ operand1: event.target.value });
  };

  handleOp2Update = event => {
    this.props.updateOp2({ operand2: event.target.value });
  };

  render() {
    return (
      <div className="ipt_panel">
        <input
          type="text"
          id="op1"
          value={this.props.operand1}
          onChange={this.handleOp1Update}
        />
        {this.props.operator}
        <input
          type="text"
          id="op2"
          value={this.props.operand2}
          onChange={this.handleOp2Update}
        />
      </div>
    );
  }
}

export default InputPanel
