import React, { Component } from "react";
import "./Product.css";

export default class Product extends Component {
    getProduct() {

    }

    render() {
        return (
            <div className="Product">
                <p>Nimi: {this.props.name}</p>
                <p>Tekijä: {this.props.auchtor}</p>
                <p>ISBN: {this.props.isbn}</p>
                <p>Julkaisuvuosi: {this.props.published}</p>
                <p>Genre: {this.props.genre}</p>
                <p>Tyyppi: {this.props.type}</p>
                <p>Paino: {this.props.weight}</p>
            </div>
        );
    }
}