import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import "./ShoppingCart.css";
import CartListing from "./CartListing";

export default class ShoppingCart extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        if(this.props.show) {
            let totalPrice = 0;
            this.props.shoppingCart.forEach((item) => {
                item.products.sort((a, b) => {
                    return a.sellingPrice <= b.sellingPrice;
                });
                for(let i = 0; i < item.amount; i++) {
                    totalPrice += item.products[i].sellingPrice;
                }
            });
            
            return (
                    <div className="ShoppingCart">
                        <h2>Ostoskori</h2>
                        { this.props.shoppingCart.length !== 0 ?
                            this.props.shoppingCart.map((item, i) => {
                                return (
                                    <CartListing key={i}
                                        item={item}
                                        decreaseAmount={() => this.props.changeCartAmount(false, item.id)}
                                        increaseAmount={() => this.props.changeCartAmount(true, item.id)}
                                        removeFromCart={() => this.props.removeFromCart(item.id)}
                                    />
                                );
                            })
                        :
                            <p className="no-products">Ostoskori on tyhjä</p>
                        }
                    <div className="Overall">
                        <h3>Yhteensä {totalPrice} euroa</h3>
                        <Button
                            bsStyle="primary"
                        >
                            Tilaa
                        </Button>
                    </div>
                </div>
            );
        } else {
            return '';
        }
    }
}