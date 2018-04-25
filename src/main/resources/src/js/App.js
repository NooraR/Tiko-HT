import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import Login from './Login'
import Search from './Search';
import { Navbar, Nav } from "react-bootstrap";
import { BrowserRouter, NavLink, Route } from 'react-router-dom';
import './App.css';
import Registration from "./Registration";
import ShoppingCart from "./ShoppingCart";
import Maintenance from './Maintenance';

class App extends Component {
    constructor(props) {
        super(props);

        this.state = {
            isLoggedIn: false,
            user: null
        }

        this.setUser = this.setUser.bind(this);
    }

    setUser(user) {
        this.setState({
            isLoggedIn: true,
            user: user
        });
    }
    render() {
        return (
            <BrowserRouter>
                <div>
                    <Navbar>
                        <Navbar.Header>
                            <Navbar.Brand>
                                <h1>Antikvariaatti</h1>
                            </Navbar.Brand>
                        </Navbar.Header>
                        <Nav>
                            <NavLink className="NavLink" to="">Haku</NavLink>
                            <NavLink className="NavLink" to="/login">Kirjautuminen</NavLink>
                            <NavLink className="NavLink" to="/registration">Rekisteröityminen</NavLink>
                            <NavLink className="NavLink" to="/shoppingCart">Ostoskori</NavLink>
                            <NavLink className="NavLink" to="/maintenance">Ylläpito</NavLink>
                        </Nav>
                    </Navbar>
                    <div className="content">
                        <Route path="" component={Search}/>
                        <Route path="/login" render={() => <Login setUser={this.setUser} />}/>
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
