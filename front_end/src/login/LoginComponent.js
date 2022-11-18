import React from "react";
import "./LoginComponent.css";
import { withRouter } from "../utils/WithRouter";

class LoginComponent extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      myText: {
        type: "text",
        value: "",
      },
    };
  }

  checkIn = () => {
    this.props.navigate("/main");
  };

  textChange = (e, q, a) => {
    const { myText } = this.state;
    this.setState(
      {
        myText: {
          type: myText.type,
          value: e.currentTarget.value,
        },
      },
      () => {
        console.log("state", this.state);
      }
    );
  };
  render() {
    const { myText } = this.state;
    let mainStyle = {
      height: "100%",
      width: "100%",
    };
    let clickStyle = {
      height: "50px",
      width: "100px",
    };
    return (
      <div style={mainStyle}>
        <span>登录</span>
        <div style={clickStyle}>
          <input
            type={myText.type}
            value={myText.value}
            onChange={this.textChange}
          />
          <button onClick={this.checkIn}>进入</button>
        </div>
      </div>
    );
  }
}

export default withRouter(LoginComponent);
