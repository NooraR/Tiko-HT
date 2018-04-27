import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Navbar, Nav, NavItem, Button, ButtonGroup } from "react-bootstrap";
import './App.css';

import Registration from "./Registration";
import Login from './Login'
import Search from './Search';
import ShoppingCart from "./ShoppingCart";
import Maintenance from "./Maintenance";

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showLogin: false,
            currentSite: "search",
            isLoggedIn: false,
            user: null,
            shoppingCart: []
        };

        this.setUser = this.setUser.bind(this);
        this.toggleLogin = this.toggleLogin.bind(this);
        this.toggleRegistration = this.toggleRegistration.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.addToCart = this.addToCart.bind(this);
        this.removeFromCart = this.removeFromCart.bind(this);
        this.changeCartAmount = this.changeCartAmount.bind(this);
        this.switchSite = this.switchSite.bind(this);
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

        if(existingIndex === -1) {
            work.amount = 1;
            shoppingCart.push(work);
            this.setState({
                shoppingCart: shoppingCart
            });
        }
    }

    changeCartAmount(increase, id) {
        let cart = this.state.shoppingCart;
        let index = cart.findIndex((item) => {
            return item.id === id;
        });
        let work = cart[index];
        if(increase) {
            work.amount++;
        } else {
            work.amount--;
        }

        cart.splice(index, 1, work);

        this.setState({
            shoppingCart: cart
        });
    }

    removeFromCart(id) {
        let cart = this.state.shoppingCart;
        let index = cart.findIndex((item) => {
            return item.id === id;
        });
        cart.splice(index, 1);
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


    switchSite(e) {
        this.setState({
            currentSite: e.target.name
        });
    }

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
                                name={this.state.currentSite === "search" ? "shoppingCart" : "search"}
                                onClick={this.switchSite}
                            >
                                {this.state.currentSite === "shoppingCart" ? "Hakuun" : "Ostoskoriin"}
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
                                    <Button bsSize="small" name="management" onClick={this.switchSite} bsStyle="danger">Ylläpito</Button>
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
                    show={this.state.currentSite === "shoppingCart"}
                    shoppingCart={this.state.shoppingCart}
                    addToCart={this.addToCart}
                    removeFromCart={this.removeFromCart}
                    changeCartAmount={this.changeCartAmount}
                />
                <Search
                    show={this.state.currentSite === "search"}
                    addToCart={this.addToCart}
                />
                {
                    this.state.currentSite === "management" && this.state.user && this.state.user.isAdmin
                    && <Maintenance />
                }
            </div>
        )
    }
}

export default App;

ReactDOM.render(
    <App />,
    document.getElementById('root')
);
