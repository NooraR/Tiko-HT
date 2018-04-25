import React, { Component } from "react";
import "./Product.css";

export default class Product extends Component {
    constructor(props) {
        super(props);

        this.name = this.props.name;
        this.auchtor = this.props.auchtor;
        this.isbn = this.props.isbn;
        this.published = this.props.published;
        this.genre = this.props.genre;
        this.weight = this.props.weight;
    }

    getProduct() {

    }

    render() {
        return (
            <div className="Product">
                <p>Nimi: {this.name}</p>
                <p>Tekijä: {this.auchtor}</p>
                <p>ISBN: {this.isbn}</p>
                <p>Julkaisuvuosi: {this.published}</p>
                <p>Genre: {this.props.genre}</p>
                <p>Tyyppi: {this.props.type}</p>
                <p>Paino: {this.props.weight}</p>
            </div>
        );
    }
}