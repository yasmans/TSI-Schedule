"use strict";
import React, {Component} from "react";
import Header from "../Header";
import Calendar from "../Calendar";

export default class App extends Component {

  render() {
    return (
        <div className="container">
          <Header/>
          <Calendar/>
        </div>
    );
  }
}