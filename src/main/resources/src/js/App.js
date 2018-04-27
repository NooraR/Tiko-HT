import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Navbar, Nav, NavItem, Button, ButtonGroup } from "react-bootstrap";
import './App.css';

import Registration from "./Registration";
import Login from './Login'
import Search from './Search';
import ShoppingCart from "./ShoppingCart";

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showLogin: false,
            showRegistration: false,
            showShoppingCart: false,
            isLoggedIn: false,
            user: null,
            shoppingCart: []
        };

        this.setUser = this.setUser.bind(this);
        this.toggleLogin = this.toggleLogin.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.toggleShoppingCart = this.toggleShoppingCart.bind(this);
        this.addToCart = this.addToCart.bind(this);
        this.removeFromCart = this.removeFromCart.bind(this);
    }

    setUser(state, user) {
        this.setState({
            isLoggedIn: state,
            user: user
        });
    }

    addToCart(work) {
        let shoppingCart = this.state.shoppingCart;
        let existingIndex = shoppingCart.findIndex((item) => {
            return item.id === work.id;
        });

        if(existingIndex !== -1) {
            work.amount = shoppingCart[existingIndex].amount + 1;
            shoppingCart.splice(existingIndex, 1, work);
            this.setState({
                shoppingCart: shoppingCart
            });
        } else {
            work.amount = 1;
            shoppingCart.push(work);
            this.setState({
                shoppingCart: shoppingCart
            });
        }
    }

    removeFromCart(id) {
        let cart = this.state.shoppingCart;
        cart.splice(id, 1);
        this.setState({
            shoppingCart: cart
        });
    }

    toggleLogin = () => {
        this.setState({
            showLogin: !this.state.showLogin,
            showRegistration: false
        });
    };

    toggleRegistration = () => {
        this.setState({
            showLogin: false,
            showRegistration: !this.state.showRegistration
        });
    };

    toggleShoppingCart = () => {
        this.setState({
            showShoppingCart: !this.state.showShoppingCart
        });
    };

    handleLogout = () => {
        fetch("/logout", {
            method: 'GET',
            credentials: "same-origin"
        })
        .then(result => {
            return result.json();
        })
        .then(json => {
            console.log(json);
            if(json.success) {
                this.setUser(false, null);
            }
        });
    };

    render() {
        return (
            <div>
                <Login showModal={this.state.showLogin} toggleModal={this.toggleLogin} setUser={this.setUser} />
                <Registration showModal={this.state.showRegistration} toggleModal={this.toggleRegistration} />
                <Navbar>
                    <Navbar.Header>
                        <Navbar.Brand>
                            <h1>Antikvariaatti</h1>
                        </Navbar.Brand>
                    </Navbar.Header>
                    <Nav>
                        <NavItem className="nav-button">
                            <Button
                                bsSize="small"
                                bsStyle="info"
                                onClick={this.toggleShoppingCart}
                            >
                                {!this.state.showShoppingCart ? "Ostoskoriin" : "Hakuun"}
                            </Button>
                        </NavItem>
                        <NavItem className="nav-button">
                        {!this.state.isLoggedIn ?
                            <ButtonGroup>
                                <Button onClick={this.toggleLogin} bsSize="small" bsStyle="success">Kirjaudu sisään</Button>
                                <Button onClick={this.toggleRegistration} bsSize="small" bsStyle="primary">Rekisteröidy</Button>
                            </ButtonGroup>
                        :
                            (this.state.user && this.state.user.isAdmin ?
                                <ButtonGroup>
                                    <Button bsSize="small" bsStyle="danger">Ylläpito</Button>
                                    <Button onClick={this.handleLogout} bsSize="small" bsStyle="primary">Kirjaudu ulos</Button>
                                </ButtonGroup>

                            :
                                <Button onClick={this.handleLogout} bsSize="small" bsStyle="primary">Kirjaudu ulos</Button>
                            )
                        }

                        </NavItem>
                    </Nav>
                </Navbar>
                <ShoppingCart
                    show={this.state.showShoppingCart}
                    shoppingCart={this.state.shoppingCart}
                    addToCart={this.addToCart}
                    removeFromCart={this.removeFromCart}
                />
                <Search
                    show={!this.state.showShoppingCart}
                    addToCart={this.addToCart}
                />
            </div>
        )
    }
}

export default App;

ReactDOM.render(
    <App />,
    document.getElementById('root')
);
