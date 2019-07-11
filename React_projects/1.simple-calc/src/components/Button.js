import React, { Component } from 'react'

class Button extends Component {
  handleClick = ()=>{
    this.props.clickHandler(this.props.name)
  }

  render() {
    return (
      <React.Fragment>
        <button id={this.props.id} className="btns" onClick={this.handleClick}>
          {this.props.name}
        </button>
      </React.Fragment>
    )
  }
}

export default Button
