import React from 'react'
import Board from './Board';
import History from './History';

class Game extends React.Component {
  constructor(props) {
    super(props)
  
    this.state = {
       history : [{
        squares: Array(9).fill(null)
       }],
       stepNumber: 0,
       xIsNext: true,
       isListAsc: true
    }
  }

  jumpTo = (move)=>{
    this.setState({
      stepNumber: move,
      xIsNext: (move%2)===0
    })
  }

  handleClick = (i) => {
    const history = this.state.history.slice(0, this.state.stepNumber+1)
    const current = history[history.length - 1]
    const new_squares = current.squares.slice()

    // Skipping the click if winner is already determined
    // or if the square is already filled
    if (calculateWinner(new_squares) || new_squares[i]) {
      return
    }

    new_squares[i] = this.state.xIsNext ? 'X' : 'O'
    this.setState({
      history: history.concat([{
        squares: new_squares
      }]),
      stepNumber: history.length,
      xIsNext: !this.state.xIsNext
    })
  }

  findPosition = (history, move) =>{
    const twoDPosition = [
      [0, 0],
      [0, 1],
      [0, 2],
      [1, 0],
      [1, 1],
      [1, 2],
      [2, 0],
      [2, 1],
      [2, 2]
    ]
    let current = history[move].squares
    let previous = history[move?move-1:0].squares
    for (let i=0; i<current.length; i++){
      if(current[i]!==previous[i]){
        return twoDPosition[i]
      }
    }
  }

  filpSortFlag = ()=>{
    this.setState({
      isListAsc: !this.state.isListAsc
    })
  }
 
  render() {
    let winning_player, winning_squares
    const history = this.state.history
    const current = history[this.state.stepNumber]
    const winner = calculateWinner(current.squares)
    
    var moves = history.map((step, move) => {
      let pos = this.findPosition(history, move);

      // const description = move?
      //   "Go to move #"+move+" ["+pos[0]+", "+pos[1]+"]":
      //   "Go to game start"
      const description = move ? (
        this.state.stepNumber === move ? (
          <b>
            Go to move # {move} [{pos[0]}, {pos[1]}]
          </b>
        ) : (
          "Go to move #" + move + " [" + pos[0] + ", " + pos[1] + "]"
        )
      ) : this.state.stepNumber === move ? (
        <b>Go to start game</b>
      ) : (
        "Go to start game"
      );

      return (
        <li key={move}>
          <button onClick={() => this.jumpTo(move)}>{description}</button>
        </li>
      );
    });


    let status
    if(winner){
      winning_player = winner[0]
      winning_squares = winner.slice(1)
      status = 'Winner: ' + winning_player
    }
    else if(!current.squares.includes(null)){
      status = "Match Drawn"
    }
    else{
      status = 'Next Player: ' + (this.state.xIsNext? 'X' : 'O')
    }

    return (
      <div className="game">
        <div className="game-board">
          <Board
            squares={current.squares}
            onClick={i => this.handleClick(i)}
            winning_squares = {winning_squares}
          />
        </div>
        <div className="game-info">
          <div>{status}</div>
          <div>
            <History 
              isListAsc = {this.state.isListAsc}
              onClick = {this.filpSortFlag}
              moves = {moves}
            />
          </div>
        </div>
      </div>
    );
  }
}

export default Game


function calculateWinner(squares) {
  const lines = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6]
  ]

  for (let i = 0; i < lines.length; i++) {
    const [a, b, c] = lines[i]
    if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
      let temp = squares[a]
      // return squares[a]
      return temp.concat(lines[i])
    }
  }

  return null
}