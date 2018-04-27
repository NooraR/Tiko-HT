import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { Navbar, Nav, Button, ButtonToolbar } from "react-bootstrap";
import { BrowserRouter, NavLink, Route } from 'react-router-dom';

import './App.css';

import Registration from "./Registration";
import ShoppingCart from "./ShoppingCart";
import Maintenance from './Maintenance';
import Login from './Login'
import Search from './Search';

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showLogin: false,
            showRegistration: false,
            isLoggedIn: false,
            user: null
        }

        this.setUser = this.setUser.bind(this);
        this.toggleLogin = this.toggleLogin.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
    }

    setUser(state, user) {
        this.setState({
            isLoggedIn: state,
            user: user
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
            <BrowserRouter>
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
                            <ButtonToolbar>
                            {!this.state.isLoggedIn ?
                                <Button onClick={this.toggleLogin}>Kirjaudu sisään</Button>
                                : <Button onClick={this.handleLogout}>Kirjaudu ulos</Button>
                            }
                            {!this.state.isLoggedIn
                                && <Button onClick={this.toggleRegistration}>Rekisteröidy</Button>}
                            </ButtonToolbar>
                            <NavLink className="NavLink" to="/shoppingCart">Ostoskori</NavLink>
                            <NavLink className="NavLink" to="/maintenance">Ylläpito</NavLink>
                        </Nav>
                    </Navbar>
                    <div className="content">
                        <Route path="" component={Search}/>
                        <Route path="/shoppingCart" component={ShoppingCart}/>
                        <Route path="/maintenance" component={Maintenance}/>
                    </div>
                </div>
            </BrowserRouter>
        )
    }
}

export default App;

ReactDOM.render(
    <App />,
    document.getElementById('root')
);
