import React from 'react'

function Button(props) {
  return (
    <button onClick={()=>props.onClick(props.name)}>
      {props.name}
    </button>
  )
}
export default Button
