import React, { Component } from 'react'
import Button from './Button';

class ButtonPanel extends Component {
  handleClick = (buttonName)=>{
    this.props.clickHandler(buttonName)
  }

  render() {
    return (
      <div className="btn_panel">
        <Button name="+" id="add" clickHandler={this.handleClick} />
        <Button name="-" id="sub" clickHandler={this.handleClick} />
        <Button name="*" id="mul" clickHandler={this.handleClick} />
        <Button name="/" id="div" clickHandler={this.handleClick} />
        <button 
          onClick={this.props.clear}
          className="clear"
        >C
        </button>
      </div>
    );
  }
}

export default ButtonPanel
