import React, { Component } from 'react'
import '../css/FormComponent.css'

class FormComponent extends Component {
  constructor(props) {
    super(props)
    this.state = {
       title: '',
       body: ''
    }
  }

  handleTitleChange = event=>{
    this.setState({
      title: event.target.value
    })
  }

  handleBodyChange = event=>{
    this.setState({
      body: event.target.value
    })
  }

  FormHandler = (event)=>{
    let {title, body} = this.state
    this.props.addData(title, body)
    event.preventDefault()
  }
  
  render() {
    var styles = {
      textAlign: 'center',
      display: this.props.viewForm ? "block" : "none"
    };
    const {title, body} = this.state
    return (
      <div
        className="center-element"
        style={{ border: 1 + "px solid black", overflow: "auto" }}
      >
        <button
          className="center-element"
          style={{ width: 40 + "%" }}
          onClick={() => this.props.onClick()}
        >
          {this.props.viewForm ? "Hide Form" : "Add data"}
        </button>
        <div className="center-element" style={styles}>
          <form onSubmit={this.FormHandler}>
            <input 
              value = {title}
              onChange = {this.handleTitleChange}
              placeholder="title" 
            />
            <textarea 
              value = {body}
              onChange = {this.handleBodyChange}
              placeholder="body"
            />
            <button type="submit">Submit</button>
            {/* <button type="reset">Reset</button> */}
          </form>
        </div>
      </div>
    );
  }
}

export default FormComponent
