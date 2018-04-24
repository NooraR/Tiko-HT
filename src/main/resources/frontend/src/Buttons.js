import React, { Component } from "react";
import { Button } from "react-bootstrap";
import './Buttons.css';

export default class Buttons extends Component {

    render() {
        return (
            <div className="NavBar">
                <h1>Antikvariaatti</h1>
                <Button>
                    <a href="ShoppingCart.js">Ostoskori</a>
                </Button>
                <Button
                >
                    <a href="Login.js">Kirjaudu</a>
                </Button>
            </div>
        );
    }
}