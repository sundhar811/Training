import React from 'react'

function Display(props) {
  return (
    <table className="display">
      <tbody>
        <tr>
          <td>Operand 1</td>
          <td>{props.operand1}</td>
        </tr>
        <tr>
          <td>Operand 2</td>
          <td>{props.operand2}</td>
        </tr>
        <tr>
          <td>Operator</td>
          <td>{props.operator}</td>
        </tr>
        <tr>
          <td>Total</td>
          <td>{props.total}</td>
        </tr>
      </tbody>
    </table>
  );
}

export default Display
