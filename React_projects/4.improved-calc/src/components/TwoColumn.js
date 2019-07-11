import React from 'react'

function TwoColumn(props) {
  return (
    <React.Fragment>
      <div>{props.column1()}</div>
      <div>{props.column2()}</div>
    </React.Fragment>
  );
}

export default TwoColumn;