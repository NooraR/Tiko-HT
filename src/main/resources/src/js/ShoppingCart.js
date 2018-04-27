import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import Product from './Product';
import "./ShoppingCart.css";

export default class ShoppingCart extends Component {

    constructor(props) {
        super(props);

        this.state = {
            onCart: []
        };

        this.onCart = props.onCart;

    }

    render() {
        return (
            <div className="content">
                <div className="ShoppingCart">
                    <h2>Ostokset:</h2>
                    {
                        this.onCart.map((result, i) => {
                            return (
                                <Product key={i} id={result.id} author={result.author} name={result.name} isbn={result.isbn} published={result.published} genre={result.genre} type={result.type} weight={result.weight} products={result.products} />
                            )
                        })
                    }
                </div>
                <div className="Overall">
                    <h3>Yhteensä:</h3>
                </div>
                <Button>
                    Siirry maksamaan
                </Button>
            </div>
        );
    }
}