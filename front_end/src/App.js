import React from "react";
import Component from "react-dom";
import logo from "./logo.svg";
import "./App.css";

import MainComponent from './components/main/MainComponent.js';


class App extends React.Component {
  
  constructor(props) {
    super(props);
    this.state = {

    };

  }


  render() {
    let values = this.state;

    return (
      <div className="main-container">
        <MainComponent />
      </div>
    );
  }
}

export default App;
