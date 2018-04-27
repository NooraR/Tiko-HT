import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Login from './Login'
import Search from './Search';
import { Navbar, Nav, Button } from "react-bootstrap";
import { BrowserRouter, NavLink, Route } from 'react-router-dom';
import './App.css';
import Registration from "./Registration";
import ShoppingCart from "./ShoppingCart";
import Maintenance from './Maintenance';

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showLogin: false,
            showRegistration: false,
            isLoggedIn: false,
            user: null
        };

        this.setUser = this.setUser.bind(this);
        this.toggleLogin = this.toggleLogin.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
    }

    setUser(user) {
        this.setState({
            isLoggedIn: true,
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

    };

    render() {
        return (
            <BrowserRouter>
                <div>
                    <Login showModal={this.state.showLogin} toggleLogin={this.toggleLogin} setUser={this.setUser} />
                    <Navbar>
                        <Navbar.Header>
                            <Navbar.Brand>
                                <h1>Antikvariaatti</h1>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            {!this.state.isLoggedIn ?
                                <Button onClick={this.toggleLogin}>Kirjaudu sisään</Button>
                                : <Button onClick={this.handleLogout}>Kirjaudu ulos</Button>
                            }
                            <NavLink className="NavLink" to="/registration">Rekisteröityminen</NavLink>
                            <NavLink className="NavLink" to="/shoppingCart">Ostoskori</NavLink>
                            <NavLink className="NavLink" to="/maintenance">Ylläpito</NavLink>
                        </Nav>
                    </Navbar>
                    <div className="content">
                        <Route path="" component={Search}/>
                        <Route path="/registration" component={Registration}/>
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
