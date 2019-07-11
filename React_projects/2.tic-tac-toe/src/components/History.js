import React, { Component } from 'react'

class History extends Component {
  render() {
    const moves = this.props.moves
    return (
      <div>
        <button onClick={this.props.onClick}>Sort moves by Asc/Desc</button>
        {
          this.props.isListAsc 
          ? <div><p>Ascending order: </p><ol>{moves}</ol></div>
          : <div><p>Descending order: </p><ol reversed>{moves.reverse()}</ol></div>
        }
      </div>
    );
  }
}

export default History
