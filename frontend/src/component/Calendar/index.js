"use strict";
import React, {Component} from "react";
import Grid  from 'react-bootstrap/lib/Grid';
import {Col, Row} from "react-bootstrap";

export default class Calendar extends Component {

  render() {
    return (
        <Grid>
          <Row className="show-grid">
            <Col xs={1} md={1}><code>&lt;{'Col xs={1} md={1}'} /&gt;</code></Col>
            <Col xs={1} md={1}><code>&lt;{'Col xs={1} md={1}'} /&gt;</code></Col>
          </Row>
        </Grid>
    );
  }
}