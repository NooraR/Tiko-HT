import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import Product from './Product';
import "./Order.css";

export default class Order extends Component {

    constructor(props) {
        super(props);

        this.state = {
            postage: 0,
            totalWeight: 0,
            shoppingCart: []
        }
    }

    addPostage() {
        if (this.totalWeight >= 2) {
            this.state.postage = 14;
        }
        else if (this.totalWeight >= 1) {
            this.state.postage = 8.4;
        }
        else if (this.totalWeight >= 0.5) {
            this.state.postage = 5.6;
        }
        else if (this.totalWeight >= 0.25) {
            this.state.postage = 2.8;
        }
        else if (this.totalWeight >= 0.1) {
            this.state.postage = 2.1;
        }
        else if (this.totalWeight >= 0.05) {
            this.state.postage = 1.4;
        }
        else {
            console.log("Error! Postikuluja ei pystytty määrittämään.");
        }
    }

    render() {
        if(this.props.show) {
            return (
                <div className="content">
                    <div className="order">
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