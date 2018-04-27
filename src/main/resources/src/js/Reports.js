import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import "./Maintenance.css";

export default class Maintenance extends Component {
    render() {
        return (
            <div>
                <Button
                    onClick="addToCart(id)"
                >
                    Hae käyttäjät-raportti
                </Button>
                <Button
                    onClick="addToCart(id)"
                >
                    Hae tuote-raportti
                </Button>
            </div>
        );
    }
}