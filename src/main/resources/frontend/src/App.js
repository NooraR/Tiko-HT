import React, { Component } from 'react';
import Login from './Login'
import Search from './Search';
import { Button } from "react-bootstrap";
import { HashRouter, NavLink, Route } from 'react-router-dom';
import './App.css';
import Registration from "./Registration";

class App extends Component {
  render() {
    return (
        <HashRouter>
            <div>
                <h1>Antikvariaatti</h1>
                    <Button><NavLink to="/search">Haku</NavLink></Button>
                    <Button><NavLink to="/login">Kirjautuminen</NavLink></Button>
                    <Button><NavLink to="/registration">Rekisteröityminen</NavLink></Button>
                <div className="content">
                    <Route path="/search" component={Search}/>
                    <Route path="/login" component={Login}/>
                    <Route path="/registration" component={Registration}/>
                </div>
            </div>
        </HashRouter>

    );
  }
}

export default App;
