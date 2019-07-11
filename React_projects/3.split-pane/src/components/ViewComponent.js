import React, { Component } from 'react'
import '../css/ViewComponent.css'

class ViewComponent extends Component {
  render() {
     const data = this.props.data;
     const current_key = this.props.current_key.toString();
     const keys = Object.keys(data).map(key => {
       return (
         <div
           className={
             current_key === key ? "currElement" : "leftElement"
           }
           key={key}
           value={key}
           onClick={() => this.props.handleClick(key)}
           onChange={this.props.handleBodyUpdate}
         >
           {current_key===key && this.props.toggleLeftEdit ? (
            <input 
              value={this.props.update_key}
              onChange={this.props.handleKeyUpdate}
            />
           ):(
             key
           )}
           {current_key===key && this.props.toggleLeftEdit ? (
             <button
               className="edit-button"
               onClick={this.props.updateTitle}
             >
               Confirm
             </button>
           ) : (
             <button
               className="edit-button"
               onClick={this.props.toggleLeftEditFlag}
             >
               Edit
             </button>
           )}
         </div>
       );
     });
     return (
       <div className="main">
         <div className="leftPane">
          {keys}
         </div>
         <div className="rightPane">
           {this.props.toggleEdit ? (
            <button 
              className="edit-button" 
              onClick={()=>this.props.updateBody(this.props.current_data)}
            >
              Confirm Update
            </button>
           ) : (
             <button className="edit-button" onClick={this.props.toggleEditFlag}>
              Edit Text
             </button>
           )}

           {this.props.toggleEdit ? (
             <textarea
               className="edit-area"
               value={this.props.current_data}
               onChange={this.props.handleUpdate}
             />
           ) : (
             this.props.current_data
           )}
         </div>
       </div>
     );
  }
}

export default ViewComponent
