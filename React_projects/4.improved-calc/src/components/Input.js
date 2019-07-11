import React, { Component } from 'react'

class Input extends Component {
  handleUpdate = event => {
    this.props.updateText(event.target.value);
  }

  render() {
    return (
      <input type="text" value={this.props.value} onChange={this.handleUpdate} />
    );
  }
}

export default Input