import React, { Component } from "react";
import { Button } from "react-bootstrap";
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
        } = this.props;

        return (
            <div>
                <div className="product">
                    <p>Nimi: {name}</p>
                    <p>Tekijä: {author}</p>
                    <p>ISBN: {isbn}</p>
                    <p>Julkaisuvuosi: {published}</p>
                    <p>Genre: {genre}</p>
                    <p>Tyyppi: {type}</p>
                    <p>Paino: {weight} kg</p>
                    <p>Saldo: {products.length}</p>
                    <Button
                        className="addToCart"
                        onClick="addToCart(id)"
                    >Lisää ostoskoriin</Button>
                </div>
            </div>
        );
    }
}