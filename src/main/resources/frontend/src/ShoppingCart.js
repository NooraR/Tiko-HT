import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import Product from './Product';
import "./ShoppingCart.css";

export default class ShoppingCart extends Component {



    render() {
        return (
            <div className="content">
                <div className="ShoppingCart">
                    <h2>Ostokset:</h2>
                    <Product />
                    <Product />
                </div>
                <div className="Overall">
                    <h3>Yhteensä:</h3>
                </div>
                <Button
                    onclick=""
                >
                    <Link to="/">Siirry maksamaan</Link>
                </Button>
            </div>
        );
    }
}