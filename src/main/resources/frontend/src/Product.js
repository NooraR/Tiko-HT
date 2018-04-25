import React, { Component } from "react";
import "./Product.css";

export default class Product extends Component {
    constructor(props) {
        super(props);

        this.id = this.props.id;
        this.auchtor = this.props.auchtor;
        this.name = this.props.name;
        this.isbn = this.props.isbn;
        this.published = this.props.published;
        this.genre = this.props.genre;
        this.type = this.props.type;
        this.weight = this.props.weight;
        this.products = this.props.products;
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