import React, { Component } from "react";
import "./Product.css";

export default class Product extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let { id,
            author,
            name,
            isbn,
            published,
            genre,
            type,
            weight,
            products
        } = this.props

        return (
            <div className="Product">
                <p>Id: {id}</p>
                <p>Nimi: {name}</p>
                <p>Tekijä: {author}</p>
                <p>ISBN: {isbn}</p>
                <p>Julkaisuvuosi: {published}</p>
                <p>Genre: {genre}</p>
                <p>Tyyppi: {type}</p>
                <p>Paino: {weight}</p>
            </div>
        );
    }
}