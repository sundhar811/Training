import React from 'react'
import Square from './Square';

class Board extends React.Component {
  winningSquare = (i)=>{
    if (this.props.winning_squares) {
      if (this.props.winning_squares.includes(i)) 
        return true
    }
    return false
  }

  renderSquare = (i)=>{
    return <Square 
      key = {i}
      value={this.props.squares[i]} 
      onClick = {()=>this.props.onClick(i)}
      className = {this.winningSquare(i)?"winner":"square"}
      />
  }

  render() {
    let row = []
    let square = []
    let index = 0
    for(let i=0; i<3; i++){
      for(let j=0; j<3; j++){
        row.push(this.renderSquare(index))
        index = index + 1
      }
      square.push(<div key={i} className="board-row">{row}</div>)
      row = []
    }

    return (
      <div>
        {square}
      </div>
    );
  }
}

export default Board