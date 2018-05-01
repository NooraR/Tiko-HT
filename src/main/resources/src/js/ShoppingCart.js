import React, { Component } from "react";
import { Button } from 'react-bootstrap';
import "./ShoppingCart.css";
import CartListing from "./CartListing";
import Order from './Order.js';

export default class ShoppingCart extends Component {

    constructor(props) {
        super(props);

        this.state = {
            showOrder: false,
            orderData: null,
            orderLoading: false
        }

        this.toggleOrder = this.toggleOrder.bind(this);
        this.makeOrder = this.makeOrder.bind(this);
    }

    toggleOrder() {
        this.setState({
            showOrder: !this.state.showOrder
        });
    }

    makeOrder() {
        this.setState({
            orderLoading: true
        });

        fetch("/order", {
            method: 'POST',
            headers: {"Content-type": "application/json; charset=UTF-8"},
            body: JSON.stringify(this.props.shoppingCart),
            credentials: "same-origin"
        })
        .then(results => {
            return results.json();
        })
        .then(json => {
            console.log(json);
            if(json.success) {
                this.setState({
                    orderData: json.data,
                    showOrder: true
                });
            }

            this.setState({
                orderLoading: false
            });
        });
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
                        <h3>Yhteensä {totalPrice.toFixed(2)} euroa</h3>
                        {this.props.loggedIn ?
                            <Button
                                bsStyle="primary"
                                onClick={this.makeOrder}
                                disabled={this.state.orderLoading || this.props.shoppingCart.length === 0}
                            >
                                {this.state.orderLoading ? "Odota" : "Tilaa"}
                            </Button>
                        :
                            <p className="no-login">Kirjaudu sisään tilataksesi</p>
                        }

                    </div>
                    {this.state.showOrder &&
                        <Order
                            visible={this.state.showOrder}
                            toggleVisibility={this.toggleOrder}
                            data={this.state.orderData}
                            refreshWorks={this.props.fetchWorks}
                        />}
                </div>
            );
        } else {
            return '';
        }
    }
}