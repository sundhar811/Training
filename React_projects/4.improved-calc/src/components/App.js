import React, { Component } from 'react'
import Button from './Button';
import Input from "./Input";
import TwoColumn from "./TwoColumn";
import ButtonPanel from "./ButtonPanel";
import InputPanel from "./InputPanel";
import DisplayBox from "./DisplayBox";
import '../css/App.css'
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

  clearAll = () => {
    this.setState({
      operand1: 0,
      operand2: 0,
      operator: "+",
      total: 0
    });
  };

  updateTotal = obj => {
    this.setState({
      total: calculate(obj)
    });
  };

  handleClick = buttonName => {
    this.setState(
      {
        operator: buttonName
      },
      () => {
        this.updateTotal(this.state);
      }
    );
  };

  handleOp1Update = text => {
    this.setState(
      {
        operand1: text
      },
      () => {
        this.updateTotal(this.state);
      }
    );
  };

  handleOp2Update = text => {
    this.setState(
      {
        operand2: text
      },
      () => {
        this.updateTotal(this.state);
      }
    );
  };

  render() {
    const { operand1, operand2, operator, total } = this.state;
    return (
      <div className="app">
        <TwoColumn 
          column1={()=>{
            return (
              <div className="calculator">
                <InputPanel>
                  <Input
                    value={operand1}
                    updateText={this.handleOp1Update}
                  />
                  {operator}
                  <Input
                    value={operand2}
                    updateText={this.handleOp2Update}
                  />
                </InputPanel>
                <ButtonPanel>
                  <Button name="+" onClick={this.handleClick} />
                  <Button name="-" onClick={this.handleClick} />
                  <Button name="*" onClick={this.handleClick} />
                  <Button name="/" onClick={this.handleClick} />
                  <Button name="C" onClick={this.clearAll} />
                </ButtonPanel>
              </div>
            )
          }} 
          column2={()=>{
            return (
              <div className="display">
                <TwoColumn
                  column1={()=>{
                    return (
                      <div className="displayName">
                        <DisplayBox>Operand 1</DisplayBox>
                        <DisplayBox>Operand 2</DisplayBox>
                        <DisplayBox>Operator</DisplayBox>
                        <DisplayBox>Total</DisplayBox>
                      </div>
                    )
                  }}
                  column2={()=>{
                    return (
                      <div className="displayValue">
                        <DisplayBox>{operand1}</DisplayBox>
                        <DisplayBox>{operand2}</DisplayBox>
                        <DisplayBox>{operator}</DisplayBox>
                        <DisplayBox>{total}</DisplayBox>
                      </div>
                    );
                  }}
                />
              </div>
            )
          }}
        /> 
      </div>
    );
  }
}

export default App