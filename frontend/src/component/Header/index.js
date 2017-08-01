"use strict";
import React, {Component} from 'react';

export default class Header extends Component {

  render() {
    return (
        <div className="page-header">
          <form className="form-horizontal">
            <div className="form-group form-group-sm">
              <div className="col-sm-9">
                <h1>TSI Calendar</h1>
              </div>
              <div className="col-sm-3">
                <p className="text-right">
                  <a id="change-lang-lv" href="/?lang=lv">lv</a> /
                  <a id="change-lang-ru" href="/?lang=ru">ru</a> /
                  <a id="change-lang-en" href="/?lang=en">en</a>
                </p>
              </div>
            </div>
          </form>
        </div>
    );
  }
}