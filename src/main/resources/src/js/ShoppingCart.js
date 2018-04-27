import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import Product from './Product';
import "./ShoppingCart.css";

export default class ShoppingCart extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        if(this.props.show) {
            return (
                <div className="content">
                    <div className="ShoppingCart">
                        <h2>Ostokset:</h2>
                        {this.props.shoppingCart.map((item, i) => {
                            return <Product
                                key={i}
                                work={item}
                                isInShoppingCart={true}
                                addToCart={() => this.props.addToCart(item)}
                                removeFromCart={() => this.props.removeFromCart(item.id)}
                            />;
                        })}
                    </div>
                    <div className="Overall">
                        <h3>Yhteensä:</h3>
                    </div>
                    <Button>
                        Siirry maksamaan
                    </Button>
                </div>
            );
        } else {
            return '';
        }
    }
}