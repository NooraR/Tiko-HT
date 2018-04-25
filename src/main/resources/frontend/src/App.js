import React, { Component } from 'react';
import Login from './Login'
import Search from './Search';
import { Navbar, NavItem, Nav } from "react-bootstrap";
import { HashRouter, NavLink, Route } from 'react-router-dom';
import './App.css';
import Registration from "./Registration";
import ShoppingCart from "./ShoppingCart";
import Maintenance from './Maintenance';

class App extends Component {
  render() {
    return (
        <HashRouter>
            <div>
                <Navbar>
                    <Navbar.Header>
                        <Navbar.Brand>
                            <h1>Antikvariaatti</h1>
                        </Navbar.Brand>
                    </Navbar.Header>
                    <Nav>
                        <NavItem componentClass="span"><NavLink className="NavLink" to="">Haku</NavLink></NavItem>
                        <NavItem componentClass="span"><NavLink className="NavLink" to="/login">Kirjautuminen</NavLink></NavItem>
                        <NavItem componentClass="span"><NavLink className="NavLink" to="/registration">Rekisteröityminen</NavLink></NavItem>
                        <NavItem componentClass="span"><NavLink className="NavLink" to="/shoppingCart">Ostoskori</NavLink></NavItem>
                        <NavItem componentClass="span"><NavLink className="NavLink" to="/maintenance">Ylläpito</NavLink></NavItem>
                    </Nav>
                </Navbar>
                <div className="content">
                    <Route path="" component={Search}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/registration" component={Registration}/>
                    <Route path="/shoppingCart" component={ShoppingCart}/>
                    <Route path="/maintenance" component={Maintenance}/>
                </div>
            </div>
        </HashRouter>
    )
  }
}

export default App;
