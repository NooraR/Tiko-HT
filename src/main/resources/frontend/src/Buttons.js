import React, { Component } from "react";
import { Button } from "react-bootstrap";
import './Buttons.css';

export default class Buttons extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="NavBar">
                <Button
                >
                    <a href="#">Ostoskori</a>
                </Button>
                <Button
                >
                    <a href="#">Kirjaudu</a>
                </Button>
            </div>
        );
    }

}