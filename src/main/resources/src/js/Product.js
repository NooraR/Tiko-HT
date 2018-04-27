import React, { Component } from "react";
import { Button } from "react-bootstrap";
import "./Product.css";

export default class Product extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        if(this.props.work) {
            let {
                author,
                name,
                isbn,
                published,
                genre,
                type,
                weight,
                products,
                amount
            } = this.props.work;

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
                        <p>Varastossa: {products.length}</p>
                        {this.props.isInShoppingCart
                            && <p>Määrä: {amount}
                                <Button
                                    className="addToCart"
                                    onClick={this.props.addToCart}
                                >+</Button>
                            </p>
                        }
                        {this.props.isInShoppingCart ?
                            <Button
                                bsStyle="danger"
                                onClick={this.props.removeFromCart}
                            >
                                Poista
                            </Button>
                            :
                            <Button
                                bsStyle="primary"
                                onClick={this.props.addToCart}
                            >
                                Lisää koriin
                            </Button>
                        }
                    </div>
                </div>
            );
        } else {
            return '';
        }
    }
}