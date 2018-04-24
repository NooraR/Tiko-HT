import React, { Component } from "react";
import "./Product.css";

export default class Product extends Component {
    render() {
        return (
            <div className="Product">
                <p>Nimi</p>
                <p>Hinta</p>
            </div>
        );
    }
}