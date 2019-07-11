import React, { Component } from 'react'
import '../css/MainComponent.css'
import ViewComponent from './ViewComponent';
import FormComponent from './FormComponent';

class MainComponent extends Component {
  constructor(props) {
    super(props);

    this.state = {
      viewForm: false,
      toggleEdit: false,
      toggleLeftEdit: false,
      current_key: 1,
      current_data: "Lorem ipsum dolor sit amet",
      data: {
        1: "Lorem ipsum dolor sit amet",
        2: "consectetur adipiscing elit",
        3: "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
        4: "Sem fringilla ut morbi tincidunt augue",
        5: "incidunt eget nullam non nisi est sit amet facilisis magna",
        6: "Aliquet nibh praesent tristique magna sit amet purus gravida",
        7: "At auctor urna nunc id",
        8: "In ante metus dictum at tempor commodo ullamcorper a",
        9: "Facilisis volutpat est velit egestas dui id ornare arcu",
        10: "Posuere sollicitudin aliquam ultrices sagittis orci a",
        11: "Ut porttitor leo a diam",
        12: "Felis eget nunc lobortis mattis",
        13: "Gravida in fermentum et sollicitudin",
        14: "Nisi vitae suscipit tellus mauris",
        15: "Etiam tempor orci eu lobortis"
      }
    };
  }

  handleClick = key => {
    this.setState({
      current_key: key,
      update_key: key,
      current_data: this.state.data[key]
    });
  };

  toggleForm = () => {
    this.setState({
      viewForm: !this.state.viewForm
    });
  };

  addData = (title, body) => {
    console.log(title, body);
    let new_data = this.state.data;
    new_data[title] = body;
    this.setState({
      data: new_data
    });
  };

  handleCurrentDataUpdate = event => {
    this.setState({
      current_data: event.target.value
    });
  };

  toggleEditFlag = () => {
    this.setState({
      toggleEdit: !this.state.toggleEdit
    });
  };

  updateBody = body => {
    let new_data = this.state.data;
    new_data[this.state.current_key] = body;
    console.log(this.state.data, "new data: ", new_data);
    this.setState({
      data: new_data,
      toggleEdit: !this.state.toggleEdit
    });
  };

  toggleLeftEditFlag = () => {
    this.setState({
      toggleLeftEdit: !this.state.toggleLeftEdit
    });
  };

  handleCurrentKeyUpdate = event => {
    this.setState({
      update_key: event.target.value
    });
  };

  updateTitle = () => {
    const current = this.state.current_key
    const updated = this.state.update_key
    const new_data = this.state.data
    for(let i=0; i<Object.keys(new_data).length;i++){
      if(current!==updated){
        Object.defineProperty(
          new_data,
          updated,
          Object.getOwnPropertyDescriptor(new_data, current)
        );
        delete new_data[current];
        return
      }
    }
  };

  render() {
    return (
      <div>
        <FormComponent
          viewForm={this.state.viewForm}
          onClick={this.toggleForm}
          addData={(title, body) => this.addData(title, body)}
        />
        <ViewComponent
          data={this.state.data}
          current_key={this.state.current_key}
          update_key={this.state.update_key}
          current_data={this.state.current_data}
          toggleEdit={this.state.toggleEdit}
          toggleEditFlag={this.toggleEditFlag}
          handleClick={key => this.handleClick(key)}
          handleUpdate={event => this.handleCurrentDataUpdate(event)}
          updateBody={body => this.updateBody(body)}
          toggleLeftEdit={this.state.toggleLeftEdit}
          toggleLeftEditFlag={this.toggleLeftEditFlag}
          handleKeyUpdate={event => this.handleCurrentKeyUpdate(event)}
          updateTitle={() => this.updateTitle()}
        />
      </div>
    );
  }
}

export default MainComponent
