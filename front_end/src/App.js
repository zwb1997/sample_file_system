import React from "react";
import "./App.css";
import MainComponent from "./components/main/MainComponent.js";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {};
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
